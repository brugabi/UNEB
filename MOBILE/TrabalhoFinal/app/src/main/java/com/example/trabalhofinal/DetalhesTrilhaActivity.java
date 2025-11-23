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
    private TextView tvDataInicio, tvDataFim, tvDuracao, tvDistancia, tvVelMedia, tvVelMaxima, tvCalorias;
    private Button btnCompartilhar, btnEditarNome, btnApagarTrilha;

    private TrilhasDAO trilhasDAO;
    private Trilha trilha;
    private long trilhaId;

    /**
     * Ponto de entrada da atividade. Responsável por inicializar a interface,
     * obter o ID da trilha, carregar seus dados e configurar os listeners de eventos.
     *
     * <p>Este método executa a seguinte sequência de inicialização:</p>
     * <ul>
     *   <li>Define o layout da tela ({@code R.layout.activity_detalhes_trilha}).</li>
     *   <li>Configura a {@link Toolbar} com um título provisório e um botão de voltar.</li>
     *   <li>Recupera o ID da trilha (`TRILHA_ID`) passado pela {@link Intent}. Se o ID for inválido (-1),
     *       exibe um erro e fecha a atividade.</li>
     *   <li>Inicializa todos os componentes da UI ({@link TextView}, {@link Button}, etc.).</li>
     *   <li>Inicializa o {@link TrilhasDAO} para acesso ao banco de dados.</li>
     *   <li>Inicializa o {@link MapView} de forma segura, passando o estado salvo e
     *       solicitando o mapa de forma assíncrona ({@code getMapAsync(this)}).</li>
     *   <li>Chama {@link #carregarDadosTrilha()} para buscar os dados da trilha no banco
     *       e popular a interface.</li>
     *   <li>Define os {@code OnClickListener}s para os botões de compartilhar, editar nome e apagar.</li>
     * </ul>
     *
     * @param savedInstanceState Se a atividade estiver sendo recriada após ter sido
     *                           destruída pelo sistema, este Bundle contém o estado salvo anteriormente.
     *                           É passado para o {@code mapView.onCreate()} para restaurar o estado do mapa.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_trilha);

        Toolbar toolbar = findViewById(R.id.toolbar_detalhes);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalhes"); // Será atualizado com o nome da trilha
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        trilhaId = getIntent().getLongExtra("TRILHA_ID", -1);
        if (trilhaId == -1) {
            Toast.makeText(this, "Erro: ID inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar componentes
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

        // INICIALIZAÇÃO SEGURA DO MAPA
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

    /**
     * Carrega os dados da trilha específica do banco de dados com base no {@code trilhaId}.
     *
     * <p>Este método é o ponto central para obter os detalhes da trilha. Ele utiliza o
     * {@code trilhaId} (recebido pela Intent) para consultar o banco de dados através
     * do {@link TrilhasDAO}.</p>
     *
     * <p>O fluxo de execução é o seguinte:</p>
     * <ul>
     *   <li>Abre a conexão com o banco de dados.</li>
     *   <li>Busca o objeto {@link Trilha} completo usando {@code trilhasDAO.getTrilhaById()}.</li>
     *   <li>Fecha imediatamente a conexão para liberar recursos.</li>
     *   <li>Verifica se a trilha foi encontrada. Se for nula, exibe um {@link Toast} de erro
     *       e encerra a atividade para evitar falhas.</li>
     *   <li>Se a trilha for encontrada, chama o método {@link #popularViews()} para preencher
     *       todos os componentes da interface com os dados carregados.</li>
     * </ul>
     */
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

    /**
     * Preenche os componentes da interface do usuário (TextViews e Toolbar) com os dados
     * do objeto {@code trilha} previamente carregado.
     *
     * <p>Este método é chamado após a trilha ser recuperada com sucesso do banco de dados.
     * Ele formata e exibe todas as informações relevantes nos seus respectivos campos na tela,
     * garantindo que os dados numéricos sejam apresentados de forma legível e com as unidades corretas.</p>
     *
     * <p>As ações realizadas incluem:</p>
     * <ul>
     *   <li>Atualizar o título da {@link Toolbar} com o nome da trilha.</li>
     *   <li>Exibir as datas de início e fim, e a duração da atividade.</li>
     *   <li>Formatar e exibir a distância total, o gasto calórico, a velocidade média e a velocidade máxima
     *       usando {@code String.format} para garantir a formatação correta dos números decimais.</li>
     * </ul>
     * <p><b>Pré-condição:</b> A variável de membro {@code trilha} deve ser não nula e conter dados válidos.</p>
     */
    private void popularViews() {
        // Título na Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(trilha.getNome());
        }

        tvDataInicio.setText("Início: " + trilha.getDataHoraInicio());
        tvDataFim.setText("Fim: " + trilha.getDataHoraFim());
        tvDuracao.setText("Duração: " + (trilha.getDuracao() != null ? trilha.getDuracao() : "--"));

        tvDistancia.setText(String.format(Locale.getDefault(), "Dist: %.2f km", trilha.getDistanciaTotal()));
        tvCalorias.setText(String.format(Locale.getDefault(), "Cal: %.1f kcal", trilha.getGastoCalorico()));
        tvVelMedia.setText(String.format(Locale.getDefault(), "Méd: %.1f km/h", trilha.getVelocidadeMedia()));
        tvVelMaxima.setText(String.format(Locale.getDefault(), "Máx: %.1f km/h", trilha.getVelocidadeMaxima()));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        if (trilha == null || isFinishing()) return;

        googleMap = map;
        googleMap.setMapType(trilha.getMapType());
        // Padding zero pois o mapa está contido no cartão
        googleMap.setPadding(0, 0, 0, 0);
        desenharPercurso();
    }

    /**
     * Desenha o percurso da trilha no objeto {@link GoogleMap}.
     *
     * <p>Este método é responsável por renderizar a rota da trilha. Ele verifica a quantidade de
     * coordenadas disponíveis e age de acordo:</p>
     * <ul>
     *   <li><b>Se houver mais de um ponto:</b> Cria uma {@link com.google.android.gms.maps.model.Polyline} conectando todos
     *       os pontos da trilha. Em seguida, calcula os limites geográficos ({@link com.google.android.gms.maps.model.LatLngBounds})
     *       que englobam todo o percurso e move a câmera do mapa para que toda a trilha seja visível
     *       com uma margem de 50 pixels. Um bloco try-catch é usado para o caso de a câmera não conseguir
     *       se mover para os limites, fornecendo um fallback que foca no ponto inicial.</li>
     *   <li><b>Se houver apenas um ponto:</b> Move a câmera para este único ponto com um nível de zoom fixo e
     *       adiciona um {@link com.google.android.gms.maps.model.Marker} para indicar a localização.</li>
     *   <li><b>Se não houver pontos:</b> O método não executa nenhuma ação.</li>
     * </ul>
     * <p><b>Pré-condições:</b> O método deve ser chamado após o mapa estar pronto (dentro ou após {@code onMapReady})
     * e as variáveis {@code googleMap} e {@code trilha} estarem devidamente inicializadas.</p>
     */
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

            try {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
            } catch (Exception e) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(percursoPoints.get(0), 15));
            }
        } else if (percursoPoints.size() == 1) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(percursoPoints.get(0), 15));
            googleMap.addMarker(new MarkerOptions().position(percursoPoints.get(0)).title("Início"));
        }
    }

    // CICLO DE VIDA MAPVIEW

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
        if (mapView != null) mapView.onDestroy();
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

    // MÉTODOS AUXILIARES

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
                popularViews(); // Atualiza também o título na Toolbar
                Toast.makeText(this, "Nome atualizado!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoApagar() {
        new AlertDialog.Builder(this).setTitle("Apagar Trilha").setMessage("Tem certeza?")
                .setPositiveButton("Apagar", (d,w) -> {
                    trilhasDAO.open(); trilhasDAO.apagarTrilha(trilha.getId()); trilhasDAO.close();
                    finish();
                }).setNegativeButton("Cancelar", null).show();
    }

    private void mostrarDialogoCompartilhar() {
        final String[] formatos = {"GPX", "KML", "JSON", "CSV"};
        new AlertDialog.Builder(this).setTitle("Escolha o formato").setItems(formatos, (d, w) -> {
            compartilharTexto(gerarDados(formatos[w]));
        }).show();
    }

    private void compartilharTexto(String texto) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(Intent.createChooser(i, "Compartilhar Trilha via"));
    }

    /**
     * Gera uma representação em String dos dados da trilha no formato especificado.
     *
     * <p>Este método atua como um despachante (dispatcher), recebendo o formato desejado como
     * parâmetro e invocando o método de geração correspondente. Ele centraliza a lógica
     * de exportação de dados, facilitando a adição de novos formatos no futuro.</p>
     *
     * <p>Os formatos suportados são:</p>
     * <ul>
     *   <li><b>GPX:</b> Chama {@link #gerarGPX(List)}.</li>
     *   <li><b>KML:</b> Chama {@link #gerarKML(List)}.</li>
     *   <li><b>JSON:</b> Utiliza a biblioteca Gson para serializar o objeto {@link Trilha} completo.</li>
     *   <li><b>CSV:</b> Chama {@link #gerarCSV(List)}.</li>
     * </ul>
     *
     * @param formato A {@link String} representando o formato de exportação desejado (ex: "GPX", "KML").
     * @return Uma {@link String} contendo os dados da trilha formatados. Retorna uma string vazia
     *         se a trilha for nula ou se o formato não for reconhecido.
     */
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