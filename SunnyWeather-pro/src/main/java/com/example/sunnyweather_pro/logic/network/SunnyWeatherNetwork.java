package com.example.sunnyweather_pro.logic.network;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sunnyweather_pro.MyApplication;
import com.example.sunnyweather_pro.logic.model.DailyResponse;
import com.example.sunnyweather_pro.logic.model.PlaceResponse;
import com.example.sunnyweather_pro.logic.model.RealtimeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*定义一个统一的网络数据源访问入口，对所有网络请求的API进行封装*/
public class SunnyWeatherNetwork {

    private static PlaceResponse placeResponse;
    private static RealtimeResponse realtimeResponse;
    private static DailyResponse dailyResponse;

    //创建Service接口的动态代理对象
    private static final PlaceService placeService = ServiceCreator.create(PlaceService.class);
    private static final WeatherService weatherService = ServiceCreator.create(WeatherService.class);

    //搜索城市地址
    public static PlaceResponse searchPlaces(String query){
        //该方法是一个异步请求，用于搜索与查询字符串 query 相关的城市地址
        Call<PlaceResponse> call = placeService.searchPlaces(query);
        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                Log.d("boge",response.toString());
                if (response.body() != null){
                    placeResponse = response.body();
                } else Toast.makeText(MyApplication.context, "响应体为空", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MyApplication.context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return placeResponse;
    }

    //获取实时天气数据
    public static RealtimeResponse getRealtimeWeather(String lng,String lat){
        Call<RealtimeResponse> call = weatherService.getRealtimeWeather(MyApplication.TOKEN, lng, lat);
        call.enqueue(new Callback<RealtimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<RealtimeResponse> call, @NonNull Response<RealtimeResponse> response) {
                if (response.body() != null){
                    realtimeResponse = response.body();
                    //Log.d("boge","realtime = " + realtimeResponse.result.realtime.toString());
                } else Toast.makeText(MyApplication.context, "响应体为空", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<RealtimeResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MyApplication.context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return realtimeResponse;
    }

    //用于获取每日天气数据
    public static DailyResponse getDailyWeather(String lng, String lat){
        Call<DailyResponse> call = weatherService.getDailyWeather(MyApplication.TOKEN, lng, lat);
        call.enqueue(new Callback<DailyResponse>() {
            @Override
            public void onResponse(@NonNull Call<DailyResponse> call, @NonNull Response<DailyResponse> response) {
                if (response.body() != null){
                    dailyResponse = response.body();
                    //Log.d("boge","daily = " + dailyResponse.result.daily.toString());
                } else Toast.makeText(MyApplication.context, "响应体为空", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<DailyResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MyApplication.context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return dailyResponse;
    }

}
