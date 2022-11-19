package com.example.klaxittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(this, Adress.class);
            startActivity(switchActivityIntent);
        });
    }
}