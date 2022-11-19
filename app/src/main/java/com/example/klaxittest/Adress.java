package com.example.klaxittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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
        TextView textView = findViewById(R.id.text);

        //Retrofit builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        Call<List<DataModel>> call = myAPICall.getData();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                //Checking for the response
                if(response.code() != 200){
                    textView.setText("Check the connection");
                    return;
                }

                //Get the data into textView
                String json = "";
                List<DataModel> data = response.body();
                for(DataModel model : data){
                    json += model.getName();
                }


                textView.append(json);
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {

            }
        });
    }
}