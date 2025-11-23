package com.example.trabalhofinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetalhesTrilhaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView tvNome, tvDataInicio, tvDataFim, tvDuracao, tvDistancia, tvVelMedia, tvVelMaxima, tvCalorias;
    private Button btnCompartilhar, btnEditarNome, btnApagarTrilha;

    private TrilhasDAO trilhasDAO;
    private Trilha trilha;
    private long trilhaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_trilha);

        Toolbar toolbar = findViewById(R.id.toolbar_detalhes);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        trilhaId = getIntent().getLongExtra("TRILHA_ID", -1);
        if (trilhaId == -1) {
            Toast.makeText(this, "Erro: ID inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvNome = findViewById(R.id.tv_detalhes_nome);
        tvDataInicio = findViewById(R.id.tv_detalhes_data_inicio);
        tvDataFim = findViewById(R.id.tv_detalhes_data_fim);
        tvDuracao = findViewById(R.id.tv_detalhes_duracao);
        tvDistancia = findViewById(R.id.tv_detalhes_distancia);
        tvVelMedia = findViewById(R.id.tv_detalhes_velocidade_media);
        tvVelMaxima = findViewById(R.id.tv_detalhes_velocidade_maxima);
        tvCalorias = findViewById(R.id.tv_detalhes_gasto_calorico);
        btnCompartilhar = findViewById(R.id.btn_compartilhar);
        btnEditarNome = findViewById(R.id.btn_editar_nome);
        btnApagarTrilha = findViewById(R.id.btn_apagar_trilha);

        trilhasDAO = new TrilhasDAO(this);

        // MAPA: Inicialização Clássica (MapView)
        mapView = findViewById(R.id.mapView_detalhes);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }

        carregarDadosTrilha();

        btnCompartilhar.setOnClickListener(v -> mostrarDialogoCompartilhar());
        btnEditarNome.setOnClickListener(v -> mostrarDialogoEditar());
        btnApagarTrilha.setOnClickListener(v -> mostrarDialogoApagar());
    }

    private void carregarDadosTrilha() {
        trilhasDAO.open();
        trilha = trilhasDAO.getTrilhaById(trilhaId);
        trilhasDAO.close();

        if (trilha == null) {
            Toast.makeText(this, "Trilha não encontrada.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        popularViews();
    }

    private void popularViews() {
        tvNome.setText(trilha.getNome());
        tvDataInicio.setText("Início: " + trilha.getDataHoraInicio());
        tvDataFim.setText("Fim: " + trilha.getDataHoraFim());
        tvDuracao.setText(trilha.getDuracao() != null ? trilha.getDuracao() : "--");

        tvDistancia.setText(String.format(Locale.getDefault(), "%.2f km", trilha.getDistanciaTotal()));
        tvCalorias.setText(String.format(Locale.getDefault(), "%.1f kcal", trilha.getGastoCalorico()));
        tvVelMedia.setText(String.format(Locale.getDefault(), "Méd: %.1f km/h", trilha.getVelocidadeMedia()));
        tvVelMaxima.setText(String.format(Locale.getDefault(), "Máx: %.1f km/h", trilha.getVelocidadeMaxima()));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        if (trilha != null) {
            googleMap.setMapType(trilha.getMapType());
            // Sem padding exagerado porque o mapa está no cartão
            googleMap.setPadding(0, 0, 0, 0);
            desenharPercurso();
        }
    }

    private void desenharPercurso() {
        if (googleMap == null || trilha == null || trilha.getCoordenadas() == null || trilha.getCoordenadas().isEmpty()) return;

        List<LatLng> percursoPoints = trilha.getCoordenadas();

        if (percursoPoints.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions().addAll(percursoPoints).color(0xFF0000FF).width(10);
            googleMap.addPolyline(polylineOptions);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : percursoPoints) builder.include(latLng);

            try {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
            } catch (Exception e) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(percursoPoints.get(0), 15));
            }
        } else if (percursoPoints.size() == 1) {
            LatLng ponto = percursoPoints.get(0);
            googleMap.addMarker(new MarkerOptions().position(ponto).title("Início"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ponto, 15));
        }
    }

    // --- CICLO DE VIDA OBRIGATÓRIO DO MAPVIEW (Para não crashar!) ---
    // Verifica sempre se mapView != null antes de chamar os métodos

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        // Limpa o mapa para evitar memory leaks
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) mapView.onSaveInstanceState(outState);
    }

    // --- MÉTODOS AUXILIARES (Iguais) ---

    private void mostrarDialogoEditar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Nome");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(trilha.getNome());
        builder.setView(input);
        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String novoNome = input.getText().toString().trim();
            if (!novoNome.isEmpty()) {
                trilha.setNome(novoNome);
                trilhasDAO.open();
                trilhasDAO.atualizarTrilha(trilha);
                trilhasDAO.close();
                popularViews();
                Toast.makeText(this, "Atualizado!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoApagar() {
        new AlertDialog.Builder(this).setTitle("Apagar").setMessage("Confirmar?").setPositiveButton("Sim", (d,w) -> {
            trilhasDAO.open(); trilhasDAO.apagarTrilha(trilha.getId()); trilhasDAO.close(); finish();
        }).setNegativeButton("Não", null).show();
    }

    private void mostrarDialogoCompartilhar() {
        final String[] formatos = {"GPX", "KML", "JSON", "CSV"};
        new AlertDialog.Builder(this).setTitle("Formato").setItems(formatos, (d, w) -> compartilharTexto(gerarDados(formatos[w]))).show();
    }

    private void compartilharTexto(String texto) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(Intent.createChooser(i, "Compartilhar"));
    }

    private String gerarDados(String formato) {
        if (trilha == null) return "";
        switch (formato) {
            case "GPX": return gerarGPX(trilha.getCoordenadas());
            case "KML": return gerarKML(trilha.getCoordenadas());
            case "JSON": return new Gson().toJson(trilha);
            case "CSV": return gerarCSV(trilha.getCoordenadas());
            default: return "";
        }
    }

    // Métodos de geração (resumidos, podes manter os teus originais)
    private String gerarGPX(List<LatLng> l) {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\"?><gpx><trk><trkseg>");
        if(l!=null) for(LatLng p:l) sb.append("<trkpt lat=\"").append(p.latitude).append("\" lon=\"").append(p.longitude).append("\"/>");
        sb.append("</trkseg></trk></gpx>"); return sb.toString();
    }
    private String gerarKML(List<LatLng> l) {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\"?><kml><Document><Placemark><LineString><coordinates>");
        if(l!=null) for(LatLng p:l) sb.append(p.longitude).append(",").append(p.latitude).append(",0 ");
        sb.append("</coordinates></LineString></Placemark></Document></kml>"); return sb.toString();
    }
    private String gerarCSV(List<LatLng> l) {
        StringBuilder sb = new StringBuilder("lat,lon\n");
        if(l!=null) for(LatLng p:l) sb.append(p.latitude).append(",").append(p.longitude).append("\n"); return sb.toString();
    }
}