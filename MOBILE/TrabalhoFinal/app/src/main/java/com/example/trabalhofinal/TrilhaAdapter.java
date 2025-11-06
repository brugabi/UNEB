
package com.example.trabalhofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class TrilhaAdapter extends ArrayAdapter<Trilha> {

    public TrilhaAdapter(@NonNull Context context, @NonNull List<Trilha> trilhas) {
        super(context, 0, trilhas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Pega o item de dados para esta posição
        Trilha trilha = getItem(position);

        // Verifica se uma view existente está sendo reutilizada, caso contrário, infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trilha, parent, false);
        }

        // Procura as views para popular os dados
        TextView tvNome = convertView.findViewById(R.id.tv_nome_trilha);
        TextView tvData = convertView.findViewById(R.id.tv_data_trilha);
        TextView tvDistancia = convertView.findViewById(R.id.tv_distancia_trilha);

        // Popula os dados na view do template usando o objeto de dados
        if (trilha != null) {
            tvNome.setText(trilha.getNome());
            tvData.setText("Data: " + trilha.getDataHoraInicio());
            tvDistancia.setText(String.format(Locale.getDefault(), "Distância: %.2f km", trilha.getDistanciaTotal()));
        }

        // Retorna a view completa para renderizar na tela
        return convertView;
    }
}
