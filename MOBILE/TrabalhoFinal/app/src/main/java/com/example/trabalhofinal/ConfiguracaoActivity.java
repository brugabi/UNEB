
package com.example.trabalhofinal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class ConfiguracaoActivity extends Activity {

    private EditText etNomeUsuario, etPeso, etAltura, etDataNascimento;
    private Spinner spinnerSexo;
    private RadioGroup rgTipoMapa, rgFormaNavegacao;
    private Button btnSalvar;

    public static final String PREFS_NAME = "ConfiguracoesPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        etNomeUsuario = findViewById(R.id.et_nome_usuario);
        etPeso = findViewById(R.id.et_peso);
        etAltura = findViewById(R.id.et_altura);
        etDataNascimento = findViewById(R.id.et_data_nascimento);
        spinnerSexo = findViewById(R.id.spinner_sexo);
        rgTipoMapa = findViewById(R.id.rg_tipo_mapa);
        rgFormaNavegacao = findViewById(R.id.rg_forma_navegacao);
        btnSalvar = findViewById(R.id.btn_salvar_configuracoes);

        // Populando o spinner de sexo
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        carregarConfiguracoes();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarConfiguracoes();
            }
        });
    }

    private void carregarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        etNomeUsuario.setText(settings.getString("nomeUsuario", ""));
        etPeso.setText(String.valueOf(settings.getFloat("peso", 0)));
        etAltura.setText(String.valueOf(settings.getFloat("altura", 0)));
        etDataNascimento.setText(settings.getString("dataNascimento", ""));
        spinnerSexo.setSelection(settings.getInt("sexo", 0));
        rgTipoMapa.check(settings.getInt("tipoMapa", R.id.rb_vetorial));
        rgFormaNavegacao.check(settings.getInt("formaNavegacao", R.id.rb_north_up));
    }

    private void salvarConfiguracoes() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("nomeUsuario", etNomeUsuario.getText().toString());
        editor.putFloat("peso", Float.parseFloat(etPeso.getText().toString()));
        editor.putFloat("altura", Float.parseFloat(etAltura.getText().toString()));
        editor.putString("dataNascimento", etDataNascimento.getText().toString());
        editor.putInt("sexo", spinnerSexo.getSelectedItemPosition());
        editor.putInt("tipoMapa", rgTipoMapa.getCheckedRadioButtonId());
        editor.putInt("formaNavegacao", rgFormaNavegacao.getCheckedRadioButtonId());

        editor.apply();

        Toast.makeText(this, "Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
