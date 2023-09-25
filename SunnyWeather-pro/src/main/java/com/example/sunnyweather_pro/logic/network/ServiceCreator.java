package com.example.sunnyweather_pro.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//创建一个Retrofit构建器
public class ServiceCreator {

    //彩云天气网络根地址
    private static final String BASE_URL = "https://api.caiyunapp.com/";

    //retrofit实例
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //返回Service接口对象
    public static <T> T create(Class<T> serviceClass){
        return retrofit.create(serviceClass);
    }

}
