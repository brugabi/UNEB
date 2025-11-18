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

public class ConfiguracaoActivity extends AppCompatActivity { // MODIFICADO AQUI

    private EditText etNomeUsuario, etPeso, etAltura, etDataNascimento;
    private Spinner spinnerSexo;
    private RadioGroup rgTipoMapa, rgFormaNavegacao;
    private Button btnSalvar;

    public static final String PREFS_NAME = "ConfiguracoesPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        Toolbar toolbar = findViewById(R.id.toolbar_configuracao);
        setSupportActionBar(toolbar); // MODIFICADO AQUI
        getSupportActionBar().setTitle("Configurações"); // Opcional: Define um título

        etNomeUsuario = findViewById(R.id.et_nome_usuario);
        etPeso = findViewById(R.id.et_peso);
        etAltura = findViewById(R.id.et_altura);
        etDataNascimento = findViewById(R.id.et_data_nascimento);
        spinnerSexo = findViewById(R.id.spinner_sexo);
        rgTipoMapa = findViewById(R.id.rg_tipo_mapa);
        rgFormaNavegacao = findViewById(R.id.rg_forma_navegacao);
        btnSalvar = findViewById(R.id.btn_salvar_configuracoes);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        carregarConfiguracoes();

        etDataNascimento.addTextChangedListener(getDateMaskListener());

        btnSalvar.setOnClickListener(v -> salvarConfiguracoes());
    }

    private void carregarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        etNomeUsuario.setText(settings.getString("nomeUsuario", ""));
        float peso = settings.getFloat("peso", 0f);
        if (peso > 0) {
            etPeso.setText(String.valueOf(peso));
        }
        float altura = settings.getFloat("altura", 0f);
        if (altura > 0) {
            etAltura.setText(String.valueOf(altura));
        }
        etDataNascimento.setText(settings.getString("dataNascimento", ""));
        spinnerSexo.setSelection(settings.getInt("sexo", 0));
        rgTipoMapa.check(settings.getInt("tipoMapa", R.id.rb_vetorial));
        rgFormaNavegacao.check(settings.getInt("formaNavegacao", R.id.rb_north_up));
    }

    private void salvarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("nomeUsuario", etNomeUsuario.getText().toString());
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
    }

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
}
