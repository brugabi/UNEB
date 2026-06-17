package com.example.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trilhas App");

        Button btnConfiguracao = findViewById(R.id.btn_configuracao);
        Button btnRegistrarTrilha = findViewById(R.id.btn_registrar_trilha);
        Button btnConsultarTrilhas = findViewById(R.id.btn_consultar_trilhas);
        Button btnCreditos = findViewById(R.id.btn_creditos);

        btnConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrarTrilha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrarTrilhaActivity.class);
                startActivity(intent);
            }
        });

        btnConsultarTrilhas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConsultarTrilhasActivity.class);
                startActivity(intent);
            }
        });

        btnCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreditosActivity.class);
                startActivity(intent);
            }
        });
    }
}
