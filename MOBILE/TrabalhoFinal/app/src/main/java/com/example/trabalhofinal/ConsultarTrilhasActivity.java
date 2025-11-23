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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_trilhas);

        Toolbar toolbar = findViewById(R.id.toolbar_consultar);
        setSupportActionBar(toolbar);

        // --- NOVO: Configuração do Botão Voltar ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Consultar Trilhas");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostra a seta de voltar
        }
        toolbar.setNavigationOnClickListener(v -> finish()); // Define a ação de fechar a tela
        // ------------------------------------------

        listViewTrilhas = findViewById(R.id.list_view_trilhas);
        trilhasDAO = new TrilhasDAO(this);

        // Configura a seleção múltipla para apagar vários itens (Batch Delete)
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

    private void carregarTrilhas() {
        trilhasDAO.open();
        trilhas = trilhasDAO.getAllTrilhas();
        trilhasDAO.close();

        adapter = new TrilhaAdapter(this, trilhas);
        listViewTrilhas.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_consultar_trilhas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_apagar_todas) {
            mostrarDialogoApagarTodas();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            // Garante que o botão físico/lógico de voltar na Toolbar funcione
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    // Classe interna para gerir a seleção múltipla (clique longo)
    private class MultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {
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

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_action, menu);
            trilhasSelecionadasParaApagar.clear();
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_contextual_delete) {
                mostrarDialogoApagarSelecionadas(mode);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            trilhasSelecionadasParaApagar.clear();
        }
    }

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