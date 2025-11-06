
package com.example.trabalhofinal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.View;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrarTrilhaActivity extends Activity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private Button btnIniciar, btnParar;
    private TextView tvVelocidade, tvDistancia, tvCalorias;
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

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_trilha);

        // --- Inicialização de Views ---
        mapView = findViewById(R.id.mapView);
        btnIniciar = findViewById(R.id.btn_iniciar_trilha);
        btnParar = findViewById(R.id.btn_parar_trilha);
        tvVelocidade = findViewById(R.id.tv_velocidade);
        tvDistancia = findViewById(R.id.tv_distancia);
        tvCalorias = findViewById(R.id.tv_calorias);
        chronometer = findViewById(R.id.chronometer);

        // --- Inicialização do Mapa ---
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // --- Inicialização do DAO ---
        trilhasDAO = new TrilhasDAO(this);

        // --- Listeners de Botões ---
        btnIniciar.setOnClickListener(v -> startTracking());
        btnParar.setOnClickListener(v -> stopTracking());

        // --- Inicialização do Cliente de Localização ---
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        createLocationCallback();
    }

    // --- Ciclo de Vida do Mapa e Activity ---
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                // Opcional: mover a câmera para a última localização conhecida
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

    // --- Lógica de Rastreamento ---

    private void startTracking() {
        if (!isTracking) {
            isTracking = true;
            btnIniciar.setEnabled(false);
            btnParar.setEnabled(true);

            // Limpar dados anteriores
            percurso.clear();
            distanciaTotal = 0;
            velocidadeMaxima = 0;
            ultimaLocalizacao = null;
            if (polyline != null) {
                polyline.remove();
            }
            polyline = googleMap.addPolyline(new PolylineOptions().color(Color.BLUE).width(10));

            // Iniciar cronômetro e registrar data/hora
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            dataHoraInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            // Iniciar atualizações de localização
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }
        }
    }

    private void stopTracking() {
        if (isTracking) {
            isTracking = false;
            btnIniciar.setEnabled(true);
            btnParar.setEnabled(false);

            // Parar cronômetro e atualizações
            chronometer.stop();
            fusedLocationClient.removeLocationUpdates(locationCallback);

            // Mostrar diálogo para salvar
            showSaveDialog();
        }
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // 5 segundos
        locationRequest.setFastestInterval(2000); // 2 segundos
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateUI(location);
                }
            }
        };
    }

    private void updateUI(Location location) {
        if (location == null || !isTracking) return;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        percurso.add(latLng);

        // Atualizar Polilinha no mapa
        polyline.setPoints(percurso);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // Calcular velocidade
        float velocidadeAtual = location.getSpeed() * 3.6f; // m/s para km/h
        tvVelocidade.setText(String.format(Locale.getDefault(), "Velocidade: %.1f km/h", velocidadeAtual));

        if (velocidadeAtual > velocidadeMaxima) {
            velocidadeMaxima = velocidadeAtual;
        }

        // Calcular distância
        if (ultimaLocalizacao != null) {
            distanciaTotal += ultimaLocalizacao.distanceTo(location);
        }
        ultimaLocalizacao = location;
        tvDistancia.setText(String.format(Locale.getDefault(), "Distância: %.2f km", distanciaTotal / 1000));

        // Calcular calorias (simplificado)
        // A fórmula real depende de peso, idade, etc. que viriam das Configurações
        // Exemplo: (MET * peso * tempo em horas)
        // Por simplicidade, usaremos uma aproximação grosseira
        float calorias = (distanciaTotal / 1000) * 60; // Apenas um exemplo
        tvCalorias.setText(String.format(Locale.getDefault(), "Calorias: %.1f kcal", calorias));
    }

    // --- Persistência de Dados ---

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvar Trilha");
        builder.setMessage("Dê um nome para a sua trilha:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String nomeTrilha = input.getText().toString();
            if (nomeTrilha.isEmpty()) {
                nomeTrilha = "Trilha de " + dataHoraInicio;
            }
            salvarTrilha(nomeTrilha);
        });
        builder.setNegativeButton("Descartar", (dialog, which) -> {
            Toast.makeText(this, "Trilha descartada", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        builder.show();
    }

    private void salvarTrilha(String nome) {
        trilhasDAO.open();

        Trilha trilha = new Trilha();
        trilha.setNome(nome);
        trilha.setDataHoraInicio(dataHoraInicio);
        trilha.setDataHoraFim(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        trilha.setDistanciaTotal(distanciaTotal / 1000); // Salvar em km
        trilha.setVelocidadeMaxima(velocidadeMaxima);

        // Calcular velocidade média
        long tempoDecorridoSegundos = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        float velocidadeMedia = (tempoDecorridoSegundos > 0) ? (distanciaTotal / tempoDecorridoSegundos) * 3.6f : 0;
        trilha.setVelocidadeMedia(velocidadeMedia);

        // Salvar percurso como JSON (ou outro formato)
        // Gson gson = new Gson();
        // trilha.setPercurso(gson.toJson(percurso));
        trilha.setPercurso(percurso.toString()); // Salva uma representação simples por enquanto

        // O cálculo de calorias real usaria os dados das SharedPreferences
        float calorias = (distanciaTotal / 1000) * 60;
        trilha.setGastoCalorico(calorias);

        long id = trilhasDAO.inserirTrilha(trilha);
        trilhasDAO.close();

        if (id != -1) {
            Toast.makeText(this, "Trilha \"" + nome + "\" salva com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Erro ao salvar a trilha.", Toast.LENGTH_SHORT).show();
        }
    }

    // --- Boilerplate (Permissões, Ciclo de Vida) ---

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
