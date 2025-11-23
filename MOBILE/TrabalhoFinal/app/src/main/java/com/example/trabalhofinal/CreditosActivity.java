package com.example.trabalhofinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreditosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        Toolbar toolbar = findViewById(R.id.toolbar_creditos);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Créditos");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Configurar o clique no link do GitHub
        View btnGithub = findViewById(R.id.container_github);
        btnGithub.setOnClickListener(v -> {
            String url = "https://github.com/brugabi/UNEB/tree/309f2e31b6e531cab0b17bc91578f49926ec9733/MOBILE/TrabalhoFinal";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}