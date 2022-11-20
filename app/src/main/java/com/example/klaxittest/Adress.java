package com.example.klaxittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-adresse.data.gouv.fr/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        Call<DataModel> call = myAPICall.getData("?q=" +"8+bd+du+port" + "&limit=4");

        ArrayList<String> autoCompleteArray = new ArrayList<>();

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.inputText);


        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                //Checking for the response
                if(response.code() != 200){
                    Log.d("oioioi", String.valueOf(response.code()));
                    return;
                }

                autoCompleteArray.clear();

                //Get the data into textView
                List<Feature> list = response.body().getFeatures();

                for(Feature f : list){
                   autoCompleteArray.add(f.getProperties().getLabel());
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autoCompleteArray);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                intent.putExtra("returnedData", adapterView.getItemAtPosition(i).toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}