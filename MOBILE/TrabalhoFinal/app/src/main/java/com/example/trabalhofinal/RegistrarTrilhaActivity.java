package com.example.trabalhofinal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RegistrarTrilhaActivity extends Activity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private Button btnIniciar, btnParar;
    private TextView tvVelocidade, tvVelocidadeMaxima, tvDistancia, tvCalorias;
    private Chronometer chronometer;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private boolean isTracking = false;
    private List<LatLng> percurso = new ArrayList<>();
    private float distanciaTotal = 0;
    private float velocidadeMaxima = 0;
    private Location ultimaLocalizacao = null;
    private Polyline polyline;
    private String dataHoraInicio;

    private TrilhasDAO trilhasDAO;
    private int navigationMode;

    // Variável global para armazenar o peso e evitar leituras repetitivas
    private float pesoUsuario = 70f;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_trilha);

        mapView = findViewById(R.id.mapView);
        btnIniciar = findViewById(R.id.btn_iniciar_trilha);
        btnParar = findViewById(R.id.btn_parar_trilha);
        tvVelocidade = findViewById(R.id.tv_velocidade);
        tvVelocidadeMaxima = findViewById(R.id.tv_velocidade_maxima);
        tvDistancia = findViewById(R.id.tv_distancia);
        tvCalorias = findViewById(R.id.tv_calorias);
        chronometer = findViewById(R.id.chronometer);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        trilhasDAO = new TrilhasDAO(this);

        btnIniciar.setOnClickListener(v -> startTracking());
        btnParar.setOnClickListener(v -> stopTracking());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        createLocationCallback();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        aplicarConfiguracoesDoMapa();
        enableMyLocation();
    }

    private void aplicarConfiguracoesDoMapa() {
        if (googleMap == null) return;

        SharedPreferences settings = getSharedPreferences(ConfiguracaoActivity.PREFS_NAME, 0);
        int mapTypeId = settings.getInt("tipoMapa", R.id.rb_vetorial);
        navigationMode = settings.getInt("formaNavegacao", R.id.rb_north_up);

        // Atualiza o peso sempre que as configurações forem carregadas
        pesoUsuario = settings.getFloat("peso", 70f);

        if (mapTypeId == R.id.rb_satelite) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                    }
                });
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void startTracking() {
        if (isTracking) return;
        isTracking = true;
        btnIniciar.setEnabled(false);
        btnParar.setEnabled(true);

        percurso.clear();
        distanciaTotal = 0;
        velocidadeMaxima = 0;
        tvVelocidadeMaxima.setText("Vel. Máxima: 0 km/h");
        ultimaLocalizacao = null;
        if (polyline != null) polyline.remove();
        polyline = googleMap.addPolyline(new PolylineOptions().color(Color.BLUE).width(10));

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        dataHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        }
    }

    private void stopTracking() {
        if (!isTracking) return;
        isTracking = false;
        btnIniciar.setEnabled(true);
        btnParar.setEnabled(false);

        chronometer.stop();
        fusedLocationClient.removeLocationUpdates(locationCallback);

        if (percurso.size() > 1) {
            showSaveDialog();
        } else {
            Toast.makeText(this, "Trilha muito curta para ser salva.", Toast.LENGTH_SHORT).show();
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500)
                .setMinUpdateIntervalMillis(250)
                .build();
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        updateUI(location);
                    }
                }
            }
        };
    }

    private void updateUI(Location location) {
        if (!isTracking || googleMap == null) return;

        float velocidadeAtual = location.getSpeed() * 3.6f;

        tvVelocidade.setText(String.format(Locale.getDefault(), "Velocidade: %.1f km/h", velocidadeAtual));

        if (velocidadeAtual > velocidadeMaxima) {
            velocidadeMaxima = velocidadeAtual;
            tvVelocidadeMaxima.setText(String.format(Locale.getDefault(), "Vel. Máxima: %.1f km/h", velocidadeMaxima));
        }

        if (ultimaLocalizacao != null) {
            float distanciaTrecho = ultimaLocalizacao.distanceTo(location);
            distanciaTotal += distanciaTrecho;
        }
        ultimaLocalizacao = location;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        percurso.add(latLng);
        polyline.setPoints(percurso);

        // Lógica de animação otimizada
        if (navigationMode == R.id.rb_course_up && location.hasBearing()) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(googleMap.getCameraPosition().zoom > 15 ? googleMap.getCameraPosition().zoom : 17)
                    .bearing(location.getBearing())
                    .tilt(45.0f)
                    .build();
            // Reduzido para 200ms para evitar conflito com atualizações rápidas do GPS
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 200, null);
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 200, null);
        }

        tvDistancia.setText(String.format(Locale.getDefault(), "Distância: %.2f km", distanciaTotal / 1000));

        // Usa a variável em memória (pesoUsuario) em vez de ler do disco
        float calorias = (distanciaTotal / 1000) * pesoUsuario * 1.036f;
        tvCalorias.setText(String.format(Locale.getDefault(), "Calorias: %.1f kcal", calorias));
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvar Trilha");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Dê um nome para a trilha");
        builder.setView(input);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String nomeTrilha = input.getText().toString().trim();
            if (nomeTrilha.isEmpty()) {
                nomeTrilha = "Trilha de " + dataHoraInicio;
            }
            salvarTrilha(nomeTrilha);
        });
        builder.setNegativeButton("Descartar", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void salvarTrilha(String nome) {
        // Bloqueia botões e avisa o utilizador
        btnParar.setEnabled(false);
        Toast.makeText(this, "A salvar trilha...", Toast.LENGTH_SHORT).show();

        // --- PASSO 1: CAPTURAR DADOS DA UI (NA THREAD PRINCIPAL) ---
        // Temos de ler estas propriedades AQUI, antes de entrar na thread secundária
        final int mapType = googleMap.getMapType();
        final long baseCronometro = chronometer.getBase();
        final long tempoAtual = SystemClock.elapsedRealtime(); // Captura o tempo exato do clique

        // Fazemos uma cópia segura da lista de pontos para a outra thread não dar erro se o GPS atualizar
        final List<LatLng> percursoSeguro = new ArrayList<>(percurso);

        final String dataFim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // --- PASSO 2: INICIAR O TRABALHO PESADO EM SEGUNDO PLANO ---
        new Thread(() -> {
            try {
                trilhasDAO.open();
                Trilha trilha = new Trilha();
                trilha.setNome(nome);
                trilha.setDataHoraInicio(dataHoraInicio);
                trilha.setDataHoraFim(dataFim);
                trilha.setDistanciaTotal(distanciaTotal / 1000);

                // Cálculos matemáticos (seguro fazer em background)
                long tempoDecorridoMillis = tempoAtual - baseCronometro;
                long tempoDecorridoSegundos = tempoDecorridoMillis / 1000;
                float velocidadeMedia = (tempoDecorridoSegundos > 0) ? (distanciaTotal / tempoDecorridoSegundos) * 3.6f : 0;
                trilha.setVelocidadeMedia(velocidadeMedia);

                trilha.setVelocidadeMaxima(velocidadeMaxima);

                long horas = TimeUnit.MILLISECONDS.toHours(tempoDecorridoMillis);
                long minutos = TimeUnit.MILLISECONDS.toMinutes(tempoDecorridoMillis) % 60;
                long segundos = TimeUnit.MILLISECONDS.toSeconds(tempoDecorridoMillis) % 60;
                String duracao = String.format(Locale.getDefault(), "%02dh %02dm %02ds", horas, minutos, segundos);
                trilha.setDuracao(duracao);

                // Usa a lista segura que copiamos lá em cima
                trilha.setCoordenadas(percursoSeguro);

                // Usa o peso que já está na memória
                float calorias = (distanciaTotal / 1000) * pesoUsuario * 1.036f;
                trilha.setGastoCalorico(calorias);

                // Usa o tipo de mapa capturado antes da thread
                trilha.setMapType(mapType);

                long id = trilhasDAO.inserirTrilha(trilha);
                trilhasDAO.close();

                // Volta para a Thread Principal SÓ para mostrar o sucesso
                runOnUiThread(() -> {
                    if (id != -1) {
                        Toast.makeText(RegistrarTrilhaActivity.this, "Trilha \"" + nome + "\" salva com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        btnParar.setEnabled(true);
                        Toast.makeText(RegistrarTrilhaActivity.this, "Erro ao salvar a trilha no banco.", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                final String mensagemErro = e.getMessage();
                runOnUiThread(() -> {
                    btnParar.setEnabled(true);
                    Toast.makeText(RegistrarTrilhaActivity.this, "Erro Crítico: " + mensagemErro, Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Permissão de localização negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        // Recarrega as configurações (incluindo o peso) ao voltar para a tela
        aplicarConfiguracoesDoMapa();
    }

    @Override
    protected void onStart() { super.onStart(); mapView.onStart(); }
    @Override
    protected void onStop() { super.onStop(); mapView.onStop(); }
    @Override
    protected void onPause() { super.onPause(); mapView.onPause(); }
    @Override
    protected void onDestroy() { super.onDestroy(); mapView.onDestroy(); }
    @Override
    public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
}