package com.example.trabalhofinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ConsultarTrilhasActivity extends AppCompatActivity {

    private ListView listViewTrilhas;
    private TrilhasDAO trilhasDAO;
    private List<Trilha> trilhas;
    private TrilhaAdapter adapter;
    private List<Trilha> trilhasSelecionadasParaApagar = new ArrayList<>();


    /**
     * Inicializa a atividade, configurando a interface do usuário e os ouvintes de eventos.
     *
     * <p>Este método realiza as seguintes tarefas principais:</p>
     * <ul>
     *   <li>Define o layout da atividade ({@code activity_consultar_trilhas}).</li>
     *   <li>Configura a {@link Toolbar} superior com título e botão de voltar.</li>
     *   <li>Inicializa o {@link ListView} e o {@link TrilhasDAO} para acesso aos dados.</li>
     *   <li>Habilita o modo de <b>seleção múltipla modal</b> ({@code CHOICE_MODE_MULTIPLE_MODAL})
     *       na lista, permitindo que o usuário pressione e segure para selecionar itens para exclusão.</li>
     *   <li>Define um listener de clique simples na lista: se não estiver em modo de seleção,
     *       abre a {@link DetalhesTrilhaActivity} passando o ID da trilha selecionada.</li>
     * </ul>
     *
     * @param savedInstanceState Estado salvo da atividade, caso esteja sendo recriada.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_trilhas);

        Toolbar toolbar = findViewById(R.id.toolbar_consultar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Consultar Trilhas");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        listViewTrilhas = findViewById(R.id.list_view_trilhas);
        trilhasDAO = new TrilhasDAO(this);

        // Configura a seleção múltipla para apagar vários itens
        listViewTrilhas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewTrilhas.setMultiChoiceModeListener(new MultiChoiceModeListener());

        // Clique simples abre os detalhes
        listViewTrilhas.setOnItemClickListener((parent, view, position, id) -> {
            // Só abre detalhes se não estivermos em modo de seleção (exclusão)
            if (listViewTrilhas.getCheckedItemCount() == 0) {
                Trilha trilhaSelecionada = trilhas.get(position);
                Intent intent = new Intent(ConsultarTrilhasActivity.this, DetalhesTrilhaActivity.class);
                intent.putExtra("TRILHA_ID", trilhaSelecionada.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarTrilhas();
    }

    /**
     * Busca todas as trilhas salvas no banco de dados e atualiza o ListView para exibi-las.
     *
     * <p>Este método é o principal responsável por popular e atualizar a lista de trilhas.
     * Ele segue um ciclo de vida de acesso a dados e atualização da UI:</p>
     * <ul>
     *   <li>Abre a conexão com o banco de dados através do {@link TrilhasDAO}.</li>
     *   <li>Executa a consulta para obter a lista completa de objetos {@link Trilha}.</li>
     *   <li>Fecha a conexão com o banco de dados para liberar recursos, uma prática essencial.</li>
     *   <li>Cria uma nova instância do {@link TrilhaAdapter} com os dados recém-buscados.</li>
     *   <li>Associa o novo adaptador ao {@link ListView}, atualizando a interface gráfica.</li>
     * </ul>
     * <p>Este método deve ser chamado sempre que a lista de trilhas precisar ser recarregada
     * (ex: no {@code onResume()} ou após uma exclusão).</p>
     */
    private void carregarTrilhas() {
        trilhasDAO.open();
        trilhas = trilhasDAO.getAllTrilhas();
        trilhasDAO.close();

        adapter = new TrilhaAdapter(this, trilhas);
        listViewTrilhas.setAdapter(adapter);
    }

    /**
     * Inicializa o menu de opções da Toolbar para esta atividade.
     *
     * <p>Este método é chamado pelo sistema Android para criar o menu de opções. Ele utiliza
     * um {@link MenuInflater} para carregar os itens definidos no arquivo de recurso
     * <b>{@code R.menu.menu_consultar_trilhas}</b> e adicioná-los ao objeto {@link Menu}.</p>
     *
     * @param menu O objeto {@link Menu} no qual os itens do menu serão inseridos.
     * @return Retorna {@code true} para que o menu seja exibido; {@code false} para não exibi-lo.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_consultar_trilhas, menu);
        return true;
    }

    /**
     * Lida com os eventos de clique nos itens do menu da Toolbar.
     *
     * <p>Este método é invocado sempre que o usuário seleciona uma opção do menu.
     * Ele identifica o item clicado através do seu ID e executa a ação correspondente.</p>
     *
     * <p>As ações implementadas são:</p>
     * <ul>
     *   <li><b>{@code R.id.menu_apagar_todas}:</b> Chama o método {@link #mostrarDialogoApagarTodas()}
     *       para iniciar o fluxo de exclusão de todas as trilhas.</li>
     *   <li><b>{@code android.R.id.home}:</b> Trata o clique no botão "Up" (seta de voltar) na Toolbar,
     *       chamando {@code finish()} para fechar a atividade e retornar à tela anterior.</li>
     * </ul>
     *
     * <p>Se o item clicado não corresponder a nenhum dos IDs tratados, a chamada é delegada
     * à implementação da superclasse.</p>
     *
     * @param item O {@link MenuItem} que foi selecionado.
     * @return Retorna {@code true} se o evento de clique foi consumido e tratado com sucesso;
     *         caso contrário, retorna o resultado da chamada à superclasse.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_apagar_todas) {
            mostrarDialogoApagarTodas();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Exibe um diálogo de confirmação antes de apagar todas as trilhas do banco de dados.
     *
     * <p>Este método constrói e exibe um {@link AlertDialog} para garantir que o usuário
     * realmente deseja executar a ação destrutiva de apagar todas as trilhas.
     * A mensagem do diálogo alerta que a ação não pode ser desfeita.</p>
     *
     * <p>O diálogo possui duas opções:</p>
     * <ul>
     *   <li><b>Botão Positivo ("Apagar Todas"):</b> Se clicado, executa a lógica de exclusão
     *       chamando {@code trilhasDAO.apagarTodasAsTrilhas()}, recarrega a lista com
     *       {@link #carregarTrilhas()} e exibe um {@link Toast} de confirmação.</li>
     *   <li><b>Botão Negativo ("Cancelar"):</b> Simplesmente fecha o diálogo sem executar
     *       nenhuma ação.</li>
     * </ul>
     */
    private void mostrarDialogoApagarTodas() {
        new AlertDialog.Builder(this)
                .setTitle("Apagar Todas as Trilhas")
                .setMessage("Tem certeza que deseja apagar TODAS as trilhas? Esta ação não pode ser desfeita.")
                .setPositiveButton("Apagar Todas", (dialog, which) -> {
                    trilhasDAO.open();
                    trilhasDAO.apagarTodasAsTrilhas();
                    trilhasDAO.close();
                    carregarTrilhas();
                    Toast.makeText(this, "Todas as trilhas foram apagadas.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Classe interna que implementa {@link AbsListView.MultiChoiceModeListener} para gerenciar
     * o modo de ação contextual (Contextual Action Mode).
     *
     * <p>Este listener é ativado quando o usuário pressiona longamente um item na lista,
     * iniciando o modo de seleção múltipla. Ele é responsável por:</p>
     * <ul>
     *   <li>Rastrear os itens selecionados e desmarcados.</li>
     *   <li>Atualizar o título da barra de ação contextual com a contagem de itens selecionados.</li>
     *   <li>Inflar e gerenciar o menu de ações contextuais (ex: botão de apagar).</li>
     *   <li>Lidar com os cliques nos itens desse menu.</li>
     *   <li>Limpar a lista de seleção quando o modo de ação é encerrado.</li>
     * </ul>
     */
    private class MultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {

        /**
         * Chamado quando o estado de seleção de um item na lista é alterado.
         *
         * <p>Adiciona ou remove a trilha da lista {@code trilhasSelecionadasParaApagar} com base
         * no estado de seleção e atualiza o título do modo de ação para refletir a contagem
         * atual de itens selecionados.</p>
         *
         * @param mode O ActionMode atual.
         * @param position A posição do item que mudou de estado.
         * @param id O ID da linha do item.
         * @param checked {@code true} se o item foi selecionado, {@code false} caso contrário.
         */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            final Trilha item = trilhas.get(position);
            if (checked) {
                trilhasSelecionadasParaApagar.add(item);
            } else {
                trilhasSelecionadasParaApagar.remove(item);
            }
            mode.setTitle(trilhasSelecionadasParaApagar.size() + " selecionada(s)");
        }

        /**
         * Chamado quando o modo de ação é criado pela primeira vez (após o primeiro clique longo).
         *
         * <p>Infla o menu de ações contextuais ({@code R.menu.menu_contextual_action}) na barra
         * de ação e limpa a lista de trilhas selecionadas para garantir um estado inicial limpo.</p>
         *
         * @param mode O ActionMode que está sendo iniciado.
         * @param menu O Menu onde as ações devem ser colocadas.
         * @return Retorna {@code true} para indicar que o modo de ação deve ser criado.
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_action, menu);
            trilhasSelecionadasParaApagar.clear();
            return true;
        }

        /**
         * Chamado para atualizar o modo de ação. Não utilizado nesta implementação.
         *
         * @param mode O ActionMode atual.
         * @param menu O menu de ações.
         * @return Retorna {@code false} pois nenhuma atualização é necessária.
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /**
         * Chamado quando um item do menu de ação contextual é clicado.
         *
         * <p>Verifica se o item clicado é o de exclusão ({@code R.id.menu_contextual_delete}).
         * Se for, invoca o método {@link #mostrarDialogoApagarSelecionadas(ActionMode)} para
         * confirmar a exclusão com o usuário.</p>
         *
         * @param mode O ActionMode atual.
         * @param item O {@link MenuItem} que foi clicado.
         * @return Retorna {@code true} se o evento foi tratado, {@code false} caso contrário.
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_contextual_delete) {
                mostrarDialogoApagarSelecionadas(mode);
                return true;
            }
            return false;
        }
        /**
         * Chamado quando o modo de ação é destruído (quando o usuário sai do modo de seleção).
         *
         * <p>Limpa a lista {@code trilhasSelecionadasParaApagar} para liberar a memória e
         * garantir que a seleção não persista para a próxima vez que o modo for ativado.</p>
         *
         * @param mode O ActionMode que está sendo encerrado.
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            trilhasSelecionadasParaApagar.clear();
        }
    }
    /**
     * Exibe um diálogo de confirmação para apagar as trilhas selecionadas no modo de ação contextual.
     *
     * <p>Este método é chamado quando o usuário clica no ícone de lixeira na barra de ação contextual.
     * Ele constrói um {@link AlertDialog} que exibe dinamicamente o número de trilhas
     * prestes a serem excluídas.</p>
     *
     * <p>O diálogo possui duas opções:</p>
     * <ul>
     *   <li><b>Botão Positivo ("Apagar"):</b> Inicia o processo de exclusão. Ele extrai os IDs
     *       das trilhas na lista {@code trilhasSelecionadasParaApagar}, chama o método
     *       {@code trilhasDAO.apagarTrilhas()}, recarrega a lista na UI com
     *       {@link #carregarTrilhas()}, finaliza o modo de ação contextual e, por fim,
     *       exibe um {@link Toast} de confirmação.</li>
     *   <li><b>Botão Negativo ("Cancelar"):</b> Fecha o diálogo sem realizar nenhuma ação.</li>
     * </ul>
     *
     * @param mode O {@link ActionMode} atual, necessário para que ele possa ser finalizado
     *             programaticamente após a conclusão da exclusão com {@code mode.finish()}.
     */
    private void mostrarDialogoApagarSelecionadas(final ActionMode mode) {
        new AlertDialog.Builder(this)
                .setTitle("Apagar Trilhas")
                .setMessage("Apagar as " + trilhasSelecionadasParaApagar.size() + " trilhas selecionadas?")
                .setPositiveButton("Apagar", (dialog, which) -> {
                    List<Long> idsParaApagar = new ArrayList<>();
                    for (Trilha t : trilhasSelecionadasParaApagar) {
                        idsParaApagar.add(t.getId());
                    }
                    trilhasDAO.open();
                    trilhasDAO.apagarTrilhas(idsParaApagar);
                    trilhasDAO.close();
                    carregarTrilhas();
                    mode.finish(); // Fecha o modo de seleção
                    Toast.makeText(this, "Trilhas selecionadas foram apagadas.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}