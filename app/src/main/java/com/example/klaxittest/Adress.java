package com.example.klaxittest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adress extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);

        ArrayList<String> autoCompleteArray = new ArrayList<>();

        //Initialize retrofit to get the answer from the API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-adresse.data.gouv.fr/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.inputText);

        //To send the good adresse to the previous activity where it will be display
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = getIntent();
            intent.putExtra("returnedData", adapterView.getItemAtPosition(i).toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        Context context = this;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, autoCompleteArray);
        autoCompleteTextView.setAdapter(adapter);

        //Call the function to update the list of adresses each time the user enters a character
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                autoComplete(retrofit, autoCompleteTextView.getText().toString(), adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    public void autoComplete(Retrofit retrofit, String s, ArrayAdapter<String> adapter) {


        //Instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        Call<DataModel> call = myAPICall.getData("?q=" + s + "&limit=4");

        //Add the adresses from the API to the Array for the autoCompleteTextView
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(@NonNull Call<DataModel> call, @NonNull Response<DataModel> response) {
                //Checking for the response
                if (response.code() != 200) {
                    return;
                }
                adapter.clear();
                assert response.body() != null;
                List<Feature> list = response.body().getFeatures();
                for (Feature f : list) {
                    adapter.add(f.getProperties().getLabel());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataModel> call, @NonNull Throwable t) {
            }
        });


    }
}