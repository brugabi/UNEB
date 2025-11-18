package com.example.trabalhofinal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreditosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        Toolbar toolbar = findViewById(R.id.toolbar_creditos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Créditos");
    }
}
