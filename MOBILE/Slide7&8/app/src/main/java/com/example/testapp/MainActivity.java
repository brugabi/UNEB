package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.buttonLocalizacao);
        button.setOnClickListener(v -> {
            Log.d("BUTTONS", "User tapped the Supabutton");
            Intent i = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(i);
        });
        Button buttongns = findViewById(R.id.buttonGNSS);
        buttongns.setOnClickListener(v -> {
            Log.d("BUTTONS", "USER TAPPED");
            Intent i = new Intent(MainActivity.this, GnssActivity.class);
            startActivity(i);
        });

    }
}