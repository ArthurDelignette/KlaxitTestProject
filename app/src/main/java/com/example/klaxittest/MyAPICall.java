package com.example.klaxittest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MyAPICall {

    @GET
    Call<DataModel> getData(@Url String url);
}
