package com.example.trabalhofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;


/**
 * Adapter personalizado para exibir uma lista de objetos {@link Trilha} em um {@link android.widget.ListView}.
 *
 * <p>Esta classe é responsável por inflar o layout de cada item da lista ({@code R.layout.list_item_trilha})
 * e preencher seus componentes visuais ({@link TextView}s) com os dados correspondentes de cada objeto
 * {@link Trilha}. Ele otimiza a performance reutilizando as views dos itens que saem da tela
 * (padrão {@code convertView}).</p>
 */
public class TrilhaAdapter extends ArrayAdapter<Trilha> {

    public TrilhaAdapter(Context context, List<Trilha> trilhas) {
        super(context, 0, trilhas);
    }

    /**
     * Obtém uma View que exibe os dados na posição especificada no conjunto de dados.
     *
     * <p>Este método é chamado para cada item na lista. Ele infla o layout do item se necessário,
     * busca o objeto {@link Trilha} correspondente à posição e preenche as views do layout
     * (nome, data e um resumo com distância e duração) com os dados da trilha.</p>
     *
     * @param position A posição do item no conjunto de dados do adaptador.
     * @param convertView A view antiga para reutilizar, se possível. Pode ser nula.
     * @param parent O ViewGroup pai ao qual esta view será anexada.
     * @return A {@link View} correspondente aos dados na posição especificada.
     */
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
        TextView tvDetalhes = convertView.findViewById(R.id.tv_detalhes_resumo);

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