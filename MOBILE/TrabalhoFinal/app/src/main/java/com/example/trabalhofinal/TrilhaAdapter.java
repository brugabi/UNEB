package com.example.trabalhofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class TrilhaAdapter extends ArrayAdapter<Trilha> {

    public TrilhaAdapter(Context context, List<Trilha> trilhas) {
        super(context, 0, trilhas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Verifica se existe uma view reutilizável, senão infla uma nova
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trilha, parent, false);
        }

        // Pega o item de dados para esta posição
        Trilha trilha = getItem(position);

        // Procura as views para popular os dados (IDs atualizados conforme o novo layout)
        TextView tvNome = convertView.findViewById(R.id.tv_nome_trilha);
        TextView tvData = convertView.findViewById(R.id.tv_data_trilha);
        TextView tvDetalhes = convertView.findViewById(R.id.tv_detalhes_resumo); // ID NOVO

        // Popula os dados nas views
        if (trilha != null) {
            tvNome.setText(trilha.getNome());

            // Formata a data (pode ser ajustado conforme o formato salvo no banco)
            tvData.setText(trilha.getDataHoraInicio());

            // Cria o texto de resumo: "5.20 km • 01h 30m 00s"
            String duracaoTexto = (trilha.getDuracao() != null) ? trilha.getDuracao() : "--";
            String resumo = String.format(Locale.getDefault(), "%.2f km • %s",
                    trilha.getDistanciaTotal(),
                    duracaoTexto);

            tvDetalhes.setText(resumo);
        }

        return convertView;
    }
}