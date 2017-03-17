package com.example.hp.pocket.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LinksApiFactory {
    public static LinksApi get() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.akademiakodu.pl")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(LinksApi.class);
    }
}
