package com.example.SunnyWeather.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//创建一个Retrofit构建器
public class ServiceCreator {

    private static final String BASE_URL = "https://api.caiyunapp.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T create(Class<T> serviceClass){
        return retrofit.create(serviceClass);
    }
}
