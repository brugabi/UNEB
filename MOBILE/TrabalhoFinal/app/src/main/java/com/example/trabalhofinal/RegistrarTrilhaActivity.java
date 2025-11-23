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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private FloatingActionButton btnCentralizar; // Novo Botão
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

    private float pesoUsuario = 70f;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Ponto de entrada da atividade, responsável por inicializar a interface do usuário,
     * configurar o mapa, preparar os serviços de localização e definir os listeners de eventos.
     *
     * <p>Este método executa a seguinte sequência de inicialização:</p>
     * <ul>
     *   <li>Define o layout da tela ({@code R.layout.activity_registrar_trilha}).</li>
     *   <li>Inicializa todos os componentes da UI: {@link MapView}, botões, {@link TextView}s e {@link Chronometer}.</li>
     *   <li>Invoca {@code mapView.onCreate()} para gerenciar o ciclo de vida do mapa e solicita o objeto
     *       {@link GoogleMap} de forma assíncrona através de {@code getMapAsync(this)}.</li>
     *   <li>Instancia o {@link TrilhasDAO} para futuras operações com o banco de dados.</li>
     *   <li>Define os {@code OnClickListener}s para os botões de iniciar, parar e centralizar o rastreamento.
     *       A lógica de centralização move a câmera para a última localização conhecida ou tenta obter uma nova.</li>
     *   <li>Inicializa o {@link FusedLocationProviderClient}, que é o ponto de entrada principal para interagir
     *       com os serviços de localização do Google Play Services.</li>
     *   <li>Chama os métodos {@link #createLocationRequest()} e {@link #createLocationCallback()} para
     *       configurar os parâmetros de precisão/intervalo e o comportamento de recebimento das atualizações de localização.</li>
     * </ul>
     *
     * @param savedInstanceState Se a atividade estiver sendo recriada, este Bundle contém o estado
     *                           salvo anteriormente. É crucial para o {@code mapView.onCreate()}
     *                           restaurar o estado do mapa corretamente.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_trilha);

        mapView = findViewById(R.id.mapView);
        btnIniciar = findViewById(R.id.btn_iniciar_trilha);
        btnParar = findViewById(R.id.btn_parar_trilha);
        btnCentralizar = findViewById(R.id.btn_centralizar); // Ligar o botão

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

        // Lógica do Botão Centralizar
        btnCentralizar.setOnClickListener(v -> {
            if (googleMap == null) return;

            // Se tivermos a última localização, movemos a câmera para lá
            if (ultimaLocalizacao != null) {
                LatLng latLng = new LatLng(ultimaLocalizacao.getLatitude(), ultimaLocalizacao.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            } else {
                // Se não força uma atualização
                enableMyLocation();
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        createLocationCallback();
    }

    /**
     * Método de callback invocado quando o mapa está totalmente carregado e pronto para ser utilizado.
     *
     * <p>Este método é o ponto central para a configuração inicial do mapa após sua inicialização
     * assíncrona. As seguintes ações são executadas em sequência:</p>
     * <ul>
     *   <li>Atribui a instância do {@link GoogleMap} recebida à variável de membro {@code googleMap},
     *       tornando-a acessível em toda a atividade.</li>
     *   <li>Chama {@link #aplicarConfiguracoesDoMapa()} para definir o tipo de mapa (vetorial/satélite)
     *       e outras preferências do usuário.</li>
     *   <li>Desativa o botão de localização padrão do Google Maps ({@code MyLocationButton}) para
     *       permitir o uso de um botão flutuante customizado ({@code btnCentralizar}).</li>
     *   <li>Invoca {@link #enableMyLocation()} para solicitar permissões (se necessário) e exibir
     *       a localização atual do usuário no mapa.</li>
     * </ul>
     *
     * @param map A instância do {@link GoogleMap} que está pronta para uso, fornecida pela API do Google Maps.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        aplicarConfiguracoesDoMapa();

        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        enableMyLocation();
    }

    /**
     * Carrega as configurações salvas pelo usuário a partir do SharedPreferences e as aplica
     * ao mapa e a outras variáveis da atividade.
     *
     * <p>Este método é chamado para garantir que as preferências do usuário, definidas na
     * {@link ConfiguracaoActivity}, sejam refletidas na tela de registro de trilha.
     * Ele realiza as seguintes ações:</p>
     * <ul>
     *   <li>Acessa o arquivo de preferências ({@code PREFS_NAME}).</li>
     *   <li>Recupera o <b>tipo de mapa</b> (vetorial ou satélite) e o aplica diretamente ao
     *       objeto {@code googleMap}.</li>
     *   <li>Obtém o <b>modo de navegação</b> (North Up ou Course Up) e armazena na variável de
     *       membro {@code navigationMode} para uso posterior durante o rastreamento.</li>
     *   <li>Carrega o <b>peso do usuário</b>, armazenando-o na variável {@code pesoUsuario}
     *       para o cálculo de calorias.</li>
     * </ul>
     * <p>Possui uma verificação de segurança para não executar caso o {@code googleMap} ainda não
     * tenha sido inicializado.</p>
     */
    private void aplicarConfiguracoesDoMapa() {
        if (googleMap == null) return;

        SharedPreferences settings = getSharedPreferences(ConfiguracaoActivity.PREFS_NAME, 0);
        int mapTypeId = settings.getInt("tipoMapa", R.id.rb_vetorial);
        navigationMode = settings.getInt("formaNavegacao", R.id.rb_north_up);

        pesoUsuario = settings.getFloat("peso", 70f);

        if (mapTypeId == R.id.rb_satelite) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * Verifica a permissão de localização e, se concedida, ativa a camada "My Location" no mapa.
     *
     * <p>Este método encapsula a lógica para exibir a localização do usuário no mapa. O fluxo é o seguinte:</p>
     * <ul>
     *   <li><b>Verifica a Permissão:</b> Checa se a permissão {@code ACCESS_FINE_LOCATION} já foi concedida.</li>
     *   <li><b>Se Concedida:</b>
     *     <ol>
     *       <li>Ativa a camada "My Location" do Google Maps, que exibe o ponto azul e o círculo de precisão.</li>
     *       <li>Solicita a última localização conhecida ao {@link FusedLocationProviderClient}.</li>
     *       <li>Se uma localização for encontrada, move a câmera do mapa para essa posição com um zoom padrão,
     *           proporcionando uma experiência inicial centrada no usuário, e armazena a localização para
     *           uso futuro pelo botão de centralizar.</li>
     *     </ol>
     *   </li>
     *   <li><b>Se Negada:</b> Inicia o fluxo padrão de solicitação de permissão do Android, exibindo
     *       a caixa de diálogo para que o usuário possa concedê-la. O resultado dessa solicitação
     *       será tratado no método de callback {@link #onRequestPermissionsResult(int, String[], int[])}.</li>
     * </ul>
     */

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);

                // Move a câmera inicial
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        ultimaLocalizacao = location; // Guarda para usar no botão
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                    }
                });
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * Inicia o processo de rastreamento de uma nova trilha.
     *
     * <p>Este método é invocado ao clicar no botão "Iniciar". Ele é responsável por configurar
     * o estado da aplicação para um novo registro, zerando todas as variáveis de medição,
     * preparando a interface e ativando as atualizações de localização.</p>
     *
     * <p>As seguintes ações são executadas em sequência:</p>
     * <ul>
     *   <li>Verifica se o rastreamento já está ativo para evitar execuções duplicadas.</li>
     *   <li>Atualiza o estado da UI, habilitando o botão "Parar" e desabilitando o "Iniciar".</li>
     *   <li>Limpa os dados da trilha anterior (coordenadas, distância, velocidade máxima).</li>
     *   <li>Remove qualquer linha ({@link Polyline}) desenhada no mapa e cria uma nova, vazia.</li>
     *   <li>Reinicia e inicia o {@link Chronometer}.</li>
     *   <li>Registra a data e hora de início da trilha.</li>
     *   <li>Solicita o início das atualizações de localização ao {@link FusedLocationProviderClient},
     *       que começarão a ser recebidas pelo {@code locationCallback}.</li>
     * </ul>
     */
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

    /**
     * Encerra a sessão de rastreamento atual, para o cronômetro e as atualizações de localização.
     *
     * <p>Este método é invocado ao clicar no botão "Parar". Ele é responsável por finalizar
     * a coleta de dados e iniciar o fluxo para salvar a trilha. As seguintes ações são executadas:</p>
     * <ul>
     *   <li>Verifica se o rastreamento está ativo para evitar execuções múltiplas.</li>
     *   <li>Atualiza o estado da UI, reabilitando o botão "Iniciar" e desabilitando o "Parar".</li>
     *   <li>Para o {@link Chronometer} para registrar a duração final.</li>
     *   <li>Remove o {@code locationCallback} do {@link FusedLocationProviderClient} para
     *       interromper o consumo de bateria pelas atualizações de localização.</li>
     *   <li>Valida se o percurso gravado possui mais de um ponto. Se sim, chama
     *       {@link #showSaveDialog()} para perguntar ao usuário se deseja salvar a trilha.</li>
     *   <li>Se o percurso for muito curto, exibe um {@link Toast} informando que a trilha
     *       não será salva.</li>
     * </ul>
     */
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

    /**
     * Cria e inicializa o objeto {@link LocationCallback} responsável por receber as atualizações de localização.
     *
     * <p>Este método define o que acontece quando o {@link FusedLocationProviderClient} entrega novos
     * dados de localização. O {@code locationCallback} é essencialmente o "ouvinte" que reage a cada
     * nova coordenada recebida durante o rastreamento.</p>
     *
     * <p>Dentro do método {@code onLocationResult}, que é o coração do callback:</p>
     * <ul>
     *   <li>Ele itera sobre a lista de {@link Location}s fornecida pelo {@link LocationResult} (que pode
     *       conter múltiplas localizações em um único lote).</li>
     *   <li>Para cada {@link Location} válida, invoca o método {@link #updateUI(Location)}, delegando a
     *       responsabilidade de atualizar a interface e calcular as métricas da trilha.</li>
     * </ul>
     */
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

    /**
     * Atualiza todos os componentes da interface e as métricas da trilha a cada nova localização recebida.
     *
     * <p>Este é o método central do rastreamento, invocado pelo {@code locationCallback} sempre que
     * uma nova coordenada é obtida. Ele é responsável por todos os cálculos em tempo real e pela
     * atualização visual da atividade.</p>
     *
     * <p>O método executa as seguintes operações em sequência:</p>
     * <ul>
     *   <li><b>Velocidade:</b> Calcula a velocidade atual (convertendo de m/s para km/h), atualiza o
     *       {@link TextView} correspondente e verifica se um novo recorde de velocidade máxima foi atingido.</li>
     *   <li><b>Distância:</b> Calcula a distância entre a localização atual e a anterior, somando-a à
     *       distância total acumulada.</li>
     *   <li><b>Atualização de Posição:</b> Armazena a localização atual como a "última localização" para o
     *       próximo cálculo de distância.</li>
     *   <li><b>Desenho no Mapa:</b> Adiciona a nova coordenada à lista do percurso e atualiza a
     *       {@link Polyline} no mapa para desenhar o trajeto.</li>
     *   <li><b>Movimento da Câmera:</b> Anima a câmera do mapa para seguir o usuário. A lógica se adapta
     *       ao {@code navigationMode} definido nas configurações:
     *       <ul>
     *           <li><b>Course Up:</b> Orienta o mapa na direção do movimento do usuário, com inclinação e zoom dinâmico.</li>
     *           <li><b>North Up (padrão):</b> Simplesmente centraliza a câmera na nova localização.</li>
     *       </ul>
     *   </li>
     *   <li><b>Métricas Finais:</b> Atualiza os {@link TextView}s de distância total e calorias gastas,
     *       recalculando as calorias com base na nova distância.</li>
     * </ul>
     *
     * @param location O novo objeto {@link Location} fornecido pelo {@link com.google.android.gms.location.FusedLocationProviderClient}.
     */
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

        if (navigationMode == R.id.rb_course_up && location.hasBearing()) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(googleMap.getCameraPosition().zoom > 15 ? googleMap.getCameraPosition().zoom : 17)
                    .bearing(location.getBearing())
                    .tilt(45.0f)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 200, null);
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 200, null);
        }

        tvDistancia.setText(String.format(Locale.getDefault(), "Distância: %.2f km", distanciaTotal / 1000));

        float calorias = (distanciaTotal / 1000) * pesoUsuario * 1.036f;
        tvCalorias.setText(String.format(Locale.getDefault(), "Calorias: %.1f kcal", calorias));
    }

    /**
     * Exibe um diálogo de alerta (`AlertDialog`) que permite ao usuário nomear e salvar a trilha recém-gravada.
     *
     * <p>Este método é chamado por {@link #stopTracking()} após a conclusão de um percurso válido.
     * Ele constrói um diálogo que contém um campo de texto ({@link EditText}) para que o usuário
     * possa inserir um nome personalizado para a trilha.</p>
     *
     * <p>O diálogo oferece duas ações principais:</p>
     * <ul>
     *   <li><b>Botão Positivo ("Salvar"):</b> Captura o nome inserido. Se o campo estiver vazio,
     *       gera um nome padrão utilizando a data e hora de início (ex: "Trilha de 2023-10-27 10:30:00").
     *       Em seguida, invoca o método {@link #salvarTrilha(String)} para persistir os dados no banco.</li>
     *   <li><b>Botão Negativo ("Descartar"):</b> Cancela a operação e fecha o diálogo. A trilha
     *       não é salva e seus dados são descartados.</li>
     * </ul>
     */
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
    /**
     * Prepara o objeto {@link Trilha} com todos os dados finais e o persiste no banco de dados.
     * <p>
     * Esta operação é executada em uma <b>thread de segundo plano</b> para evitar o bloqueio da
     * interface do usuário (UI Thread), que poderia causar uma experiência ruim ou um erro de
     * "Application Not Responding" (ANR) caso a escrita no banco de dados demore.
     *
     * <p>O fluxo de execução é o seguinte:</p>
     * <ol>
     *   <li><b>Preparação na UI Thread:</b>
     *     <ul>
     *       <li>Desabilita o botão 'Parar' para evitar cliques duplicados.</li>
     *       <li>Exibe um {@link Toast} informando ao usuário que o salvamento começou.</li>
     *       <li>Captura todos os dados necessários que ainda estão na memória (tipo do mapa,
     *           tempo do cronômetro, etc.).</li>
     *       <li>Cria uma cópia segura da lista de coordenadas para evitar qualquer
     *           modificação concorrente.</li>
     *     </ul>
     *   </li>
     *   <li><b>Execução na Background Thread:</b>
     *     <ul>
     *       <li>Abre a conexão com o banco de dados.</li>
     *       <li>Constrói um novo objeto {@link Trilha} e o popula com todos os dados: nome,
     *           datas, distância, velocidades, etc.</li>
     *       <li>Calcula a <b>duração total</b> e a <b>velocidade média</b> com base no tempo
     *           do cronômetro.</li>
     *       <li>Insere o objeto no banco de dados usando {@code trilhasDAO.inserirTrilha(trilha)}.</li>
     *       <li>Fecha a conexão com o banco de dados para liberar recursos.</li>
     *     </ul>
     *   </li>
     *   <li><b>Feedback na UI Thread (usando {@code runOnUiThread}):</b>
     *     <ul>
     *       <li>Verifica o resultado da inserção. Se for bem-sucedida, exibe um {@link Toast}
     *           de sucesso e fecha a atividade.</li>
     *       <li>Se ocorrer um erro, reabilita o botão 'Parar' e exibe uma mensagem de falha.</li>
     *       <li>Um bloco {@code try-catch} captura exceções críticas e também exibe o erro na UI.</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * @param nome O nome da trilha, fornecido pelo usuário ou gerado automaticamente.
     */

    private void salvarTrilha(String nome) {
        btnParar.setEnabled(false);
        Toast.makeText(this, "A salvar trilha...", Toast.LENGTH_SHORT).show();

        final int mapType = googleMap.getMapType();
        final long baseCronometro = chronometer.getBase();
        final long tempoAtual = SystemClock.elapsedRealtime();

        final List<LatLng> percursoSeguro = new ArrayList<>(percurso);
        final String dataFim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        new Thread(() -> {
            try {
                trilhasDAO.open();
                Trilha trilha = new Trilha();
                trilha.setNome(nome);
                trilha.setDataHoraInicio(dataHoraInicio);
                trilha.setDataHoraFim(dataFim);
                trilha.setDistanciaTotal(distanciaTotal / 1000);

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

                trilha.setCoordenadas(percursoSeguro);

                float calorias = (distanciaTotal / 1000) * pesoUsuario * 1.036f;
                trilha.setGastoCalorico(calorias);

                trilha.setMapType(mapType);

                long id = trilhasDAO.inserirTrilha(trilha);
                trilhasDAO.close();

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