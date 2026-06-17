package com.example.trabalhofinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class ConfiguracaoActivity extends AppCompatActivity {

    private EditText etPeso, etAltura, etDataNascimento;
    private Spinner spinnerSexo;
    private RadioGroup rgTipoMapa, rgFormaNavegacao;
    private Button btnSalvar;

    public static final String PREFS_NAME = "ConfiguracoesPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        Toolbar toolbar = findViewById(R.id.toolbar_configuracao);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Configurações");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());


        etPeso = findViewById(R.id.et_peso);
        etAltura = findViewById(R.id.et_altura);
        etAltura.setFilters(new android.text.InputFilter[]{});
        etDataNascimento = findViewById(R.id.et_data_nascimento);
        spinnerSexo = findViewById(R.id.spinner_sexo);
        rgTipoMapa = findViewById(R.id.rg_tipo_mapa);
        rgFormaNavegacao = findViewById(R.id.rg_forma_navegacao);
        btnSalvar = findViewById(R.id.btn_salvar_configuracoes);


        /* Configura o Spinner de Sexo usando o layout personalizado (spinner_item)
         Isto garante que o texto fica PRETO */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        carregarConfiguracoes();

        // Adiciona as máscaras automáticas para os campos de data de nascimento e altura.
        etDataNascimento.addTextChangedListener(getDateMaskListener());
        etAltura.addTextChangedListener(getHeightMaskListener());

        btnSalvar.setOnClickListener(v -> salvarConfiguracoes());
    }

    /**     * Recupera as preferências salvas no SharedPreferences e preenche os campos da interface.
     *
     * <p>Esta função realiza as seguintes operações:</p>
     * <ul>
     *   <li>Acessa o arquivo de preferências definido por <code>PREFS_NAME</code>.</li>
     *   <li>Carrega o <b>peso</b> e define no campo correspondente (se houver valor salvo).</li>
     *   <li>Carrega a <b>altura</b> e a formata para o padrão decimal (ex: 1.75) antes de exibir.</li>
     *   <li>Preenche a <b>data de nascimento</b> e seleciona a opção correta no Spinner de <b>sexo</b>.</li>
     *   <li>Marca os RadioButtons corretos para <b>Tipo de Mapa</b> (Vetorial/Satélite) e <b>Forma de Navegação</b> (North Up/Course Up),
     *       usando valores padrão caso não existam configurações prévias.</li>
     * </ul>
     */
    private void carregarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


        float peso = settings.getFloat("peso", 0f);
        if (peso > 0) {
            etPeso.setText(String.valueOf(peso));
        }

        float altura = settings.getFloat("altura", 0f);
        if (altura > 0) {
            // Formata para exibir (ex: 1.75) ao carregar
            etAltura.setText(String.format(Locale.US, "%.2f", altura));
        }

        etDataNascimento.setText(settings.getString("dataNascimento", ""));
        spinnerSexo.setSelection(settings.getInt("sexo", 0));
        rgTipoMapa.check(settings.getInt("tipoMapa", R.id.rb_vetorial));
        rgFormaNavegacao.check(settings.getInt("formaNavegacao", R.id.rb_north_up));
    }


    /**
     * Captura os dados inseridos na interface e salva nas SharedPreferences.
     *
     * <p>Este método realiza as seguintes etapas:</p>
     * <ul>
     *   <li>Inicia a edição do arquivo de preferências <code>ConfiguracoesPrefs</code>.</li>
     *   <li>Tenta converter e salvar o <b>peso</b> e a <b>altura</b> como números flutuantes.
     *       Em caso de erro de formatação (campo vazio ou inválido), salva o valor 0.</li>
     *   <li>Salva a <b>data de nascimento</b> como String simples.</li>
     *   <li>Salva o índice da opção selecionada no Spinner de <b>sexo</b>.</li>
     *   <li>Salva os IDs dos RadioButtons selecionados para <b>Tipo de Mapa</b> e <b>Forma de Navegação</b>.</li>
     *   <li>Aplica as alterações de forma assíncrona usando <code>editor.apply()</code>.</li>
     *   <li>Exibe uma mensagem de sucesso e encerra a atividade atual.</li>
     * </ul>
     */
    private void salvarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        try {
            editor.putFloat("peso", Float.parseFloat(etPeso.getText().toString()));
        } catch (NumberFormatException e) {
            editor.putFloat("peso", 0f);
        }

        try {
            editor.putFloat("altura", Float.parseFloat(etAltura.getText().toString()));
        } catch (NumberFormatException e) {
            editor.putFloat("altura", 0f);
        }

        editor.putString("dataNascimento", etDataNascimento.getText().toString());
        editor.putInt("sexo", spinnerSexo.getSelectedItemPosition());
        editor.putInt("tipoMapa", rgTipoMapa.getCheckedRadioButtonId());
        editor.putInt("formaNavegacao", rgFormaNavegacao.getCheckedRadioButtonId());

        editor.apply();

        Toast.makeText(this, "Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Cria e retorna um TextWatcher responsável por aplicar uma máscara de data
     * no formato (##/##/####) enquanto o usuário digita.
     *
     * <p>O funcionamento baseia-se em:</p>
     * <ul>
     *   <li>Remover todos os caracteres não numéricos da entrada.</li>
     *   <li>Reconstruir a string adicionando as barras (/) nas posições corretas.</li>
     *   <li>Controlar a flag <code>isUpdating</code> para evitar loops infinitos (recursão de chamada).</li>
     *   <li>Manter o cursor de texto sempre no final da string formatada.</li>
     * </ul>
     *
     * @return Um objeto {@link TextWatcher} configurado para a máscara de data.
     */

    // Máscara para DATA (##/##/####) */
    private TextWatcher getDateMaskListener() {
        return new TextWatcher() {
            private boolean isUpdating;
            private String old = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().replaceAll("[^\\d]", "");
                String formatted = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : "##/##/####".toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        formatted += m;
                        continue;
                    }
                    try {
                        formatted += str.charAt(i);
                        i++;
                    } catch (Exception e) {
                        break;
                    }
                }

                isUpdating = true;
                etDataNascimento.setText(formatted);
                etDataNascimento.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    /**
     * Cria e retorna um TextWatcher responsável por aplicar uma máscara de altura
     * no formato decimal (X.XX) enquanto o usuário digita.
     *
     * <p>O funcionamento baseia-se em:</p>
     * <ul>
     *   <li>Tratar a entrada como uma sequência pura de dígitos (removendo pontos ou vírgulas).</li>
     *   <li>Limitar a entrada a 3 dígitos significativos (ex: 175 para 1.75m).</li>
     *   <li>Dividir o valor inteiro por 100 para obter a representação em metros.</li>
     *   <li>Formatar o resultado sempre com duas casas decimais usando <code>Locale.US</code> (ponto como separador).</li>
     *   <li>Controlar a flag <code>isUpdating</code> para evitar loops infinitos ao atualizar o texto programaticamente.</li>
     * </ul>
     *
     * @return Um objeto {@link TextWatcher} configurado para a máscara de altura.
     */

    //* Máscara para ALTURA (1.75)
    private TextWatcher getHeightMaskListener() {
        return new TextWatcher() {
            private boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^\\d]", "");

                if (str.isEmpty()) {
                    return;
                }

                try {
                    long valorInt = Long.parseLong(str);

                    String strValor = String.valueOf(valorInt);

                    if (strValor.length() > 3) {
                        strValor = strValor.substring(0, 3);
                        valorInt = Long.parseLong(strValor);
                    }

                    double valorFinal = valorInt / 100.0;


                    String formatted = String.format(Locale.US, "%.2f", valorFinal);

                    isUpdating = true;
                    etAltura.setText(formatted);
                    etAltura.setSelection(formatted.length()); // Põe o cursor no fim

                } catch (NumberFormatException e) {
                    // Ignora erros de conversão
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }
}