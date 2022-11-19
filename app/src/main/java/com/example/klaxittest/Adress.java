package com.example.klaxittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        TextView textView = findViewById(R.id.text);

        //Retrofit builder url = https://run.mocky.io/v3/c38ef967-0c43-4cbb-b4a0-1f330e2d33b7
        // https://api-adresse.data.gouv.fr/search/?q=8+bd+du+port
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-adresse.data.gouv.fr/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        Call<DataModel> call = myAPICall.getData("?q=" +"8+bd+du+port");

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                //Checking for the response
                if(response.code() != 200){
                    textView.setText("Check the connection");
                    return;
                }

                //Get the data into textView
                List<Feature> list = response.body().getFeatures();

                for(Feature f : list){
                    textView.append(f.getProperties().getLabel());
                }
                
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }
}