package com.example.trabalhofinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetalhesTrilhaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView tvNome, tvDataInicio, tvDataFim, tvDistancia, tvVelMedia, tvVelMaxima, tvCalorias;
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

        trilhaId = getIntent().getLongExtra("TRILHA_ID", -1);
        if (trilhaId == -1) {
            Toast.makeText(this, "Erro: Trilha não encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvNome = findViewById(R.id.tv_detalhes_nome);
        tvDataInicio = findViewById(R.id.tv_detalhes_data_inicio);
        tvDataFim = findViewById(R.id.tv_detalhes_data_fim);
        tvDistancia = findViewById(R.id.tv_detalhes_distancia);
        tvVelMedia = findViewById(R.id.tv_detalhes_velocidade_media);
        tvVelMaxima = findViewById(R.id.tv_detalhes_velocidade_maxima);
        tvCalorias = findViewById(R.id.tv_detalhes_gasto_calorico);
        btnCompartilhar = findViewById(R.id.btn_compartilhar);
        btnEditarNome = findViewById(R.id.btn_editar_nome);
        btnApagarTrilha = findViewById(R.id.btn_apagar_trilha);

        trilhasDAO = new TrilhasDAO(this);
        carregarDadosTrilha();

        mapView = findViewById(R.id.mapView_detalhes);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnCompartilhar.setOnClickListener(v -> mostrarDialogoCompartilhar());
        btnEditarNome.setOnClickListener(v -> mostrarDialogoEditar());
        btnApagarTrilha.setOnClickListener(v -> mostrarDialogoApagar());
    }

    private void carregarDadosTrilha() {
        trilhasDAO.open();
        trilha = trilhasDAO.getTrilhaById(trilhaId);
        trilhasDAO.close();

        if (trilha == null) {
            Toast.makeText(this, "Erro ao carregar os detalhes da trilha.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        popularViews();
    }

    private void popularViews() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(trilha.getNome());
        }
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
        desenharPercurso();
    }

    private void mostrarDialogoEditar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Nome da Trilha");

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
                Toast.makeText(this, "Nome atualizado!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void mostrarDialogoApagar() {
        new AlertDialog.Builder(this)
                .setTitle("Apagar Trilha")
                .setMessage("Tem certeza que deseja apagar a trilha '" + trilha.getNome() + "'?")
                .setPositiveButton("Apagar", (dialog, which) -> {
                    trilhasDAO.open();
                    trilhasDAO.apagarTrilha(trilha.getId());
                    trilhasDAO.close();
                    Toast.makeText(this, "Trilha apagada!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void desenharPercurso() {
        if (googleMap == null || trilha.getPercurso() == null || trilha.getPercurso().isEmpty()) return;
        List<LatLng> percursoPoints = parsePercursoString(trilha.getPercurso());
        if (percursoPoints.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions().addAll(percursoPoints).color(0xFF0000FF).width(10);
            googleMap.addPolyline(polylineOptions);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : percursoPoints) {
                builder.include(latLng);
            }
            LatLngBounds bounds = builder.build();
            int padding = 100;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } else if (percursoPoints.size() == 1) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(percursoPoints.get(0), 15));
        }
    }

    private void mostrarDialogoCompartilhar() {
        final String[] formatos = {"GPX", "KML", "JSON", "CSV"};
        new AlertDialog.Builder(this)
                .setTitle("Escolha o formato de compartilhamento")
                .setItems(formatos, (dialog, which) -> {
                    String formatoEscolhido = formatos[which];
                    String dadosParaCompartilhar = gerarDados(formatoEscolhido);
                    compartilharTexto(dadosParaCompartilhar, formatoEscolhido);
                })
                .show();
    }

    private String gerarDados(String formato) {
        List<LatLng> percurso = parsePercursoString(trilha.getPercurso());
        switch (formato) {
            case "GPX": return gerarGPX(percurso);
            case "KML": return gerarKML(percurso);
            case "JSON": return gerarJSON();
            case "CSV": return gerarCSV(percurso);
            default: return "";
        }
    }

    private void compartilharTexto(String texto, String formato) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Dados da Trilha: " + trilha.getNome());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Compartilhar Trilha via"));
    }

    private String gerarGPX(List<LatLng> percurso) {
        StringBuilder gpx = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        gpx.append("<gpx version=\"1.1\" creator=\"TrilhasApp\">\n");
        gpx.append("  <trk>\n");
        gpx.append("    <name>").append(trilha.getNome()).append("</name>\n");
        gpx.append("    <trkseg>\n");
        for (LatLng ponto : percurso) {
            gpx.append("      <trkpt lat=\"").append(ponto.latitude).append("\" lon=\"").append(ponto.longitude).append("\"></trkpt>\n");
        }
        gpx.append("    </trkseg>\n");
        gpx.append("  </trk>\n");
        gpx.append("</gpx>\n");
        return gpx.toString();
    }

    private String gerarKML(List<LatLng> percurso) {
        StringBuilder kml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        kml.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        kml.append("  <Document>\n");
        kml.append("    <name>").append(trilha.getNome()).append("</name>\n");
        kml.append("    <Placemark>\n");
        kml.append("      <name>Percurso</name>\n");
        kml.append("      <LineString>\n");
        kml.append("        <coordinates>\n");
        for (LatLng ponto : percurso) {
            kml.append("          ").append(ponto.longitude).append(",").append(ponto.latitude).append(",0\n");
        }
        kml.append("        </coordinates>\n");
        kml.append("      </LineString>\n");
        kml.append("    </Placemark>\n");
        kml.append("  </Document>\n");
        kml.append("</kml>\n");
        return kml.toString();
    }

    private String gerarJSON() {
        return new Gson().toJson(trilha);
    }

    private String gerarCSV(List<LatLng> percurso) {
        StringBuilder csv = new StringBuilder("latitude,longitude\n");
        for (LatLng ponto : percurso) {
            csv.append(ponto.latitude).append(",").append(ponto.longitude).append("\n");
        }
        return csv.toString();
    }

    private List<LatLng> parsePercursoString(String percursoStr) {
        List<LatLng> latLngs = new ArrayList<>();
        if (percursoStr == null || percursoStr.isEmpty()) return latLngs;
        Pattern pattern = Pattern.compile("\\(([-+]?[0-9]*\\.[0-9]+),([-+]?[0-9]*\\.[0-9]+)\\)");
        Matcher matcher = pattern.matcher(percursoStr);
        while (matcher.find()) {
            try {
                double lat = Double.parseDouble(matcher.group(1));
                double lng = Double.parseDouble(matcher.group(2));
                latLngs.add(new LatLng(lat, lng));
            } catch (Exception e) {
                // Ignora pontos malformados
            }
        }
        return latLngs;
    }

    @Override
    protected void onResume() { super.onResume(); mapView.onResume(); }
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
