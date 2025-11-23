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

        trilhaId = getIntent().getLongExtra("TRILHA_ID", -1);
        if (trilhaId == -1) {
            Toast.makeText(this, "Erro: Trilha não encontrada", Toast.LENGTH_SHORT).show();
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
        // Exibindo a duração salva diretamente do objeto
        tvDuracao.setText("Duração: " + (trilha.getDuracao() != null ? trilha.getDuracao() : "--"));
        tvDistancia.setText(String.format(Locale.getDefault(), "Distância: %.2f km", trilha.getDistanciaTotal()));
        tvVelMedia.setText(String.format(Locale.getDefault(), "Vel. Média: %.1f km/h", trilha.getVelocidadeMedia()));
        tvVelMaxima.setText(String.format(Locale.getDefault(), "Vel. Máxima: %.1f km/h", trilha.getVelocidadeMaxima()));
        tvCalorias.setText(String.format(Locale.getDefault(), "Calorias: %.1f kcal", trilha.getGastoCalorico()));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        if (trilha != null) {
            googleMap.setMapType(trilha.getMapType());
        }
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
        if (googleMap == null || trilha.getCoordenadas() == null || trilha.getCoordenadas().isEmpty()) return;
        
        List<LatLng> percursoPoints = trilha.getCoordenadas();

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
                    compartilharTexto(dadosParaCompartilhar);
                })
                .show();
    }

    private String gerarDados(String formato) {
        List<LatLng> percurso = trilha.getCoordenadas();
        if (percurso == null) percurso = new ArrayList<>();
        
        switch (formato) {
            case "GPX": return gerarGPX(percurso);
            case "KML": return gerarKML(percurso);
            case "JSON": return gerarJSON();
            case "CSV": return gerarCSV(percurso);
            default: return "";
        }
    }

    private void compartilharTexto(String texto) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Dados da Trilha: " + trilha.getNome());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Compartilhar Trilha via"));
    }

    private String getDescricaoTrilha() {
        String duracao = trilha.getDuracao() != null ? trilha.getDuracao() : "--";
        return String.format(Locale.getDefault(),
                "Início: %s\nFim: %s\nDuração: %s\nDistância: %.2f km\nVel. Média: %.1f km/h\nVel. Máxima: %.1f km/h\nCalorias: %.1f kcal",
                trilha.getDataHoraInicio(), trilha.getDataHoraFim(), duracao,
                trilha.getDistanciaTotal(), trilha.getVelocidadeMedia(),
                trilha.getVelocidadeMaxima(), trilha.getGastoCalorico());
    }

    private String gerarGPX(List<LatLng> percurso) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<gpx version=\"1.1\" creator=\"TrilhasApp\" xmlns=\"http://www.topografix.com/GPX/1/1\">\n");
        sb.append("  <trk>\n");
        sb.append("    <name>").append(trilha.getNome() != null ? trilha.getNome() : "Trilha").append("</name>\n");
        sb.append("    <desc>").append(getDescricaoTrilha().replace("\n", ", ")).append("</desc>\n");
        sb.append("    <trkseg>\n");
        for (LatLng ponto : percurso) {
            sb.append("      <trkpt lat=\"").append(ponto.latitude).append("\" lon=\"").append(ponto.longitude).append("\" />\n");
        }
        sb.append("    </trkseg>\n");
        sb.append("  </trk>\n");
        sb.append("</gpx>");
        return sb.toString();
    }

    private String gerarKML(List<LatLng> percurso) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        sb.append("  <Document>\n");
        sb.append("    <name>").append(trilha.getNome() != null ? trilha.getNome() : "Trilha").append("</name>\n");
        sb.append("    <Placemark>\n");
        sb.append("      <name>Percurso</name>\n");
        sb.append("      <description>").append(getDescricaoTrilha()).append("</description>\n");
        sb.append("      <LineString>\n");
        sb.append("        <coordinates>\n");
        for (LatLng ponto : percurso) {
            sb.append("          ").append(ponto.longitude).append(",").append(ponto.latitude).append(",0\n");
        }
        sb.append("        </coordinates>\n");
        sb.append("      </LineString>\n");
        sb.append("    </Placemark>\n");
        sb.append("  </Document>\n");
        sb.append("</kml>");
        return sb.toString();
    }

    private String gerarJSON() {
        // Agora o Gson serializa a duração automaticamente porque ela está no objeto Trilha
        return new Gson().toJson(trilha);
    }

    private String gerarCSV(List<LatLng> percurso) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Nome: ").append(trilha.getNome()).append("\n");
        sb.append("# ").append(getDescricaoTrilha().replace("\n", "\n# ")).append("\n");
        sb.append("latitude,longitude\n");
        for (LatLng ponto : percurso) {
            sb.append(ponto.latitude).append(",").append(ponto.longitude).append("\n");
        }
        return sb.toString();
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
