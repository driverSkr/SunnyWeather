package com.example.SunnyWeather.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.SunnyWeather.SunnyWeatherApplication;
import com.example.SunnyWeather.logic.model.Place;
import com.google.gson.Gson;

public class PlaceDao {

    //用于将Place对象存储到SharedPreferences文件中
    public static void savePlace(Place place){
        //edit()方法来获取编辑器
        //通过GSON将Place对象转成一个JSON字符串
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putString("place",new Gson().toJson(place));
        editor.apply();
        Log.d("boge","调用了savePlace方法");
    }

    public static Place getSavedPlace(){
        //将JSON字符串从SharedPreferences文件中读取出来
        String placeJson = sharedPreferences().getString("place","");
        //通过GSON将JSON字符串解析成Place对象并返回
        return new Gson().fromJson(placeJson,Place.class);
    }

    //用于判断是否有数据已被存储
    public static boolean isPlaceSaved(){
        return sharedPreferences().contains("place");
    }

    private static SharedPreferences sharedPreferences(){
        Log.d("boge","调用了sharedPreferences方法");
        return SunnyWeatherApplication.context.
                getSharedPreferences("sunny_weather", Context.MODE_PRIVATE);
    }
}
