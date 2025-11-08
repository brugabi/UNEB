package com.example.trabalhofinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ConsultarTrilhasActivity extends Activity {

    private ListView listViewTrilhas;
    private TrilhasDAO trilhasDAO;
    private List<Trilha> trilhas;
    private TrilhaAdapter adapter;
    private List<Trilha> trilhasSelecionadasParaApagar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_trilhas);

        listViewTrilhas = findViewById(R.id.list_view_trilhas);
        trilhasDAO = new TrilhasDAO(this);

        // Configura a ListView para seleção múltipla no modo de ação contextual
        listViewTrilhas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewTrilhas.setMultiChoiceModeListener(new MultiChoiceModeListener());

        // Clique simples para ver detalhes
        listViewTrilhas.setOnItemClickListener((parent, view, position, id) -> {
            Trilha trilhaSelecionada = trilhas.get(position);
            Intent intent = new Intent(ConsultarTrilhasActivity.this, DetalhesTrilhaActivity.class);
            intent.putExtra("TRILHA_ID", trilhaSelecionada.getId());
            startActivity(intent);
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

    // --- Menu da ActionBar (Apagar Todas / Intervalo) ---

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_consultar_trilhas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_apagar_todas) {
            mostrarDialogoApagarTodas();
            return true;
        } else if (itemId == R.id.menu_apagar_intervalo) {
            iniciarSelecaoDataIntervalo();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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

    private void iniciarSelecaoDataIntervalo() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialogInicio = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String dataInicio = String.format(Locale.US, "%d-%02d-%02d 00:00:00", year, month + 1, dayOfMonth);
            DatePickerDialog datePickerDialogFim = new DatePickerDialog(this, (view2, year2, month2, dayOfMonth2) -> {
                String dataFim = String.format(Locale.US, "%d-%02d-%02d 23:59:59", year2, month2 + 1, dayOfMonth2);
                mostrarDialogoApagarIntervalo(dataInicio, dataFim);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialogFim.setTitle("Selecione a Data Final");
            datePickerDialogFim.show();
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialogInicio.setTitle("Selecione a Data de Início");
        datePickerDialogInicio.show();
    }

    private void mostrarDialogoApagarIntervalo(String dataInicio, String dataFim) {
        new AlertDialog.Builder(this)
                .setTitle("Apagar por Intervalo")
                .setMessage(String.format("Apagar trilhas entre %s e %s?", dataInicio.substring(0,10), dataFim.substring(0,10)))
                .setPositiveButton("Apagar", (dialog, which) -> {
                    trilhasDAO.open();
                    trilhasDAO.apagarTrilhasPorIntervalo(dataInicio, dataFim);
                    trilhasDAO.close();
                    carregarTrilhas();
                    Toast.makeText(this, "Trilhas no intervalo foram apagadas.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // --- Listener para o Modo de Ação Contextual (Seleção Múltipla) ---

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
            return false; // Não há necessidade de atualizar o menu dinamicamente
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
                .setMessage("Tem certeza que deseja apagar as " + trilhasSelecionadasParaApagar.size() + " trilhas selecionadas?")
                .setPositiveButton("Apagar", (dialog, which) -> {
                    List<Long> idsParaApagar = new ArrayList<>();
                    for (Trilha t : trilhasSelecionadasParaApagar) {
                        idsParaApagar.add(t.getId());
                    }
                    trilhasDAO.open();
                    trilhasDAO.apagarTrilhas(idsParaApagar);
                    trilhasDAO.close();
                    carregarTrilhas(); // Recarrega a lista do banco de dados
                    mode.finish(); // Fecha o modo de ação contextual
                    Toast.makeText(this, "Trilhas selecionadas foram apagadas.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
