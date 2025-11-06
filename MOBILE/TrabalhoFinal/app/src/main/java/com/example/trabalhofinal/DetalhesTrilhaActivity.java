
package com.example.trabalhofinal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetalhesTrilhaActivity extends Activity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    private TextView tvNome, tvDataInicio, tvDataFim, tvDistancia, tvVelMedia, tvVelMaxima, tvCalorias;

    private TrilhasDAO trilhasDAO;
    private Trilha trilha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_trilha);

        long trilhaId = getIntent().getLongExtra("TRILHA_ID", -1);
        if (trilhaId == -1) {
            Toast.makeText(this, "Erro: Trilha não encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // --- Inicialização de Views ---
        tvNome = findViewById(R.id.tv_detalhes_nome);
        tvDataInicio = findViewById(R.id.tv_detalhes_data_inicio);
        tvDataFim = findViewById(R.id.tv_detalhes_data_fim);
        tvDistancia = findViewById(R.id.tv_detalhes_distancia);
        tvVelMedia = findViewById(R.id.tv_detalhes_velocidade_media);
        tvVelMaxima = findViewById(R.id.tv_detalhes_velocidade_maxima);
        tvCalorias = findViewById(R.id.tv_detalhes_gasto_calorico);

        // --- Carregar dados da trilha ---
        trilhasDAO = new TrilhasDAO(this);
        trilhasDAO.open();
        trilha = trilhasDAO.getTrilhaById(trilhaId);
        trilhasDAO.close();

        if (trilha == null) {
            Toast.makeText(this, "Erro ao carregar os detalhes da trilha.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // --- Preencher Views ---
        popularViews();

        // --- Inicialização do Mapa ---
        mapView = findViewById(R.id.mapView_detalhes);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void popularViews() {
        tvNome.setText("Nome: " + trilha.getNome());
        tvDataInicio.setText("Início: " + trilha.getDataHoraInicio());
        tvDataFim.setText("Fim: " + trilha.getDataHoraFim());
        tvDistancia.setText(String.format(Locale.getDefault(), "Distância: %.2f km", trilha.getDistanciaTotal()));
        tvVelMedia.setText(String.format(Locale.getDefault(), "Vel. Média: %.1f km/h", trilha.getVelocidadeMedia()));
        tvVelMaxima.setText(String.format(Locale.getDefault(), "Vel. Máxima: %.1f km/h", trilha.getVelocidadeMaxima()));
        tvCalorias.setText(String.format(Locale.getDefault(), "Calorias: %.1f kcal", trilha.getGastoCalorico()));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        desenharPercurso();
    }

    private void desenharPercurso() {
        if (googleMap == null || trilha.getPercurso() == null || trilha.getPercurso().isEmpty()) {
            return;
        }

        List<LatLng> percursoPoints = parsePercursoString(trilha.getPercurso());

        if (percursoPoints.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(percursoPoints)
                    .color(0xFF0000FF) // Azul
                    .width(10);
            googleMap.addPolyline(polylineOptions);

            // Centralizar a câmera para mostrar todo o percurso
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : percursoPoints) {
                builder.include(latLng);
            }
            LatLngBounds bounds = builder.build();
            int padding = 100; // Espaçamento em pixels
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } else if (percursoPoints.size() == 1) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(percursoPoints.get(0), 15));
        }
    }

    private List<LatLng> parsePercursoString(String percursoStr) {
        List<LatLng> latLngs = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\(([-+]?[0-9]*\\.[0-9]+),([-+]?[0-9]*\\.[0-9]+)\\)");
        Matcher matcher = pattern.matcher(percursoStr);
        while (matcher.find()) {
            double lat = Double.parseDouble(matcher.group(1));
            double lng = Double.parseDouble(matcher.group(2));
            latLngs.add(new LatLng(lat, lng));
        }
        return latLngs;
    }

    // --- Ciclo de Vida do MapView ---
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
