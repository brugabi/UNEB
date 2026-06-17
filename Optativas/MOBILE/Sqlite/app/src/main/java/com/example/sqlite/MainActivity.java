package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonGetRoute = findViewById(R.id.buttonGetRoute);
        buttonGetRoute.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GetRouteActivity.class);
            startActivity(intent);
        });
    }
}
