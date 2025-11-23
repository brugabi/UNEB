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

    // Removi etNomeUsuario
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

        // Removido findViewById do nome
        etPeso = findViewById(R.id.et_peso);
        etAltura = findViewById(R.id.et_altura);
        etAltura.setFilters(new android.text.InputFilter[]{});
        etDataNascimento = findViewById(R.id.et_data_nascimento);
        spinnerSexo = findViewById(R.id.spinner_sexo);
        rgTipoMapa = findViewById(R.id.rg_tipo_mapa);
        rgFormaNavegacao = findViewById(R.id.rg_forma_navegacao);
        btnSalvar = findViewById(R.id.btn_salvar_configuracoes);

// ... dentro do onCreate ...

        // Configura o Spinner de Sexo usando o nosso layout personalizado (spinner_item)
        // Isto garante que o texto fica PRETO
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, R.layout.spinner_item); // MUDOU AQUI: usa R.layout.spinner_item

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        // ... resto do código ...

        carregarConfiguracoes();

        // Adiciona as máscaras automáticas
        etDataNascimento.addTextChangedListener(getDateMaskListener());
        etAltura.addTextChangedListener(getHeightMaskListener()); // Nova máscara para altura

        btnSalvar.setOnClickListener(v -> salvarConfiguracoes());
    }

    private void carregarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        // Removido carregamento do nome

        float peso = settings.getFloat("peso", 0f);
        if (peso > 0) {
            etPeso.setText(String.valueOf(peso));
        }

        float altura = settings.getFloat("altura", 0f);
        if (altura > 0) {
            // Formata para exibir bonito (ex: 1.75) ao carregar
            etAltura.setText(String.format(Locale.US, "%.2f", altura));
        }

        etDataNascimento.setText(settings.getString("dataNascimento", ""));
        spinnerSexo.setSelection(settings.getInt("sexo", 0));
        rgTipoMapa.check(settings.getInt("tipoMapa", R.id.rb_vetorial));
        rgFormaNavegacao.check(settings.getInt("formaNavegacao", R.id.rb_north_up));
    }

    private void salvarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        // Removido salvamento do nome

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
        finish(); // Fecha a tela após salvar (opcional, mas recomendado)
    }

    // Máscara para DATA (##/##/####)
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

    // NOVA Máscara para ALTURA (Corrigida para permitir digitar)
// NOVA Máscara para ALTURA (Corrigida e Testada)
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

                // 1. Limpa tudo o que não é número
                String str = s.toString().replaceAll("[^\\d]", "");

                // Se estiver vazio, não faz nada
                if (str.isEmpty()) {
                    return;
                }

                try {
                    // 2. Converte para Long para remover zeros à esquerda (ex: "0017" vira 17)
                    long valorInt = Long.parseLong(str);

                    // 3. Verifica se passou de 3 dígitos significativos (ex: 175 = 1.75m)
                    String strValor = String.valueOf(valorInt);
                    if (strValor.length() > 3) {
                        // Se digitou demais, mantém apenas os 3 primeiros números reais
                        strValor = strValor.substring(0, 3);
                        valorInt = Long.parseLong(strValor);
                    }

                    // 4. Divide por 100 para criar os decimais
                    double valorFinal = valorInt / 100.0;

                    // 5. Formata
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