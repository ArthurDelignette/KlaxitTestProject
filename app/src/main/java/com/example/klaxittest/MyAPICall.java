package com.example.klaxittest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPICall {

    @GET("c38ef967-0c43-4cbb-b4a0-1f330e2d33b7")
    Call<List<DataModel>> getData();
}
