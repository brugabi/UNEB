
package com.example.trabalhofinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ConsultarTrilhasActivity extends Activity {

    private ListView listViewTrilhas;
    private TrilhasDAO trilhasDAO;
    private List<Trilha> trilhas;
    private TrilhaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_trilhas);

        listViewTrilhas = findViewById(R.id.list_view_trilhas);
        trilhasDAO = new TrilhasDAO(this);

        // Registrar a ListView para o menu de contexto
        registerForContextMenu(listViewTrilhas);

        // Listener para clique normal (abrir detalhes)
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

    // --- Menu de Contexto para Editar/Apagar ---

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_trilha, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Trilha trilhaSelecionada = trilhas.get(info.position);

        int itemId = item.getItemId();
        if (itemId == R.id.menu_editar) {
            mostrarDialogoEditar(trilhaSelecionada);
            return true;
        } else if (itemId == R.id.menu_apagar) {
            mostrarDialogoApagar(trilhaSelecionada);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void mostrarDialogoEditar(final Trilha trilha) {
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
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Nome atualizado!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void mostrarDialogoApagar(final Trilha trilha) {
        new AlertDialog.Builder(this)
                .setTitle("Apagar Trilha")
                .setMessage("Tem certeza que deseja apagar a trilha '" + trilha.getNome() + "'?")
                .setPositiveButton("Apagar", (dialog, which) -> {
                    trilhasDAO.open();
                    trilhasDAO.apagarTrilha(trilha.getId());
                    trilhasDAO.close();
                    trilhas.remove(trilha);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Trilha apagada!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}
