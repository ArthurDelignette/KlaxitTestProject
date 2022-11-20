package com.example.klaxittest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Adress.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppCompatTextView  myPosition = findViewById(R.id.myPosition);
        addButton = findViewById(R.id.addButton);
        if(resultCode==RESULT_OK && requestCode==1){
            String msg = data.getStringExtra("returnedData");
            myPosition.setText(msg);
            addButton.setText(R.string.modify);

        }
    }
}