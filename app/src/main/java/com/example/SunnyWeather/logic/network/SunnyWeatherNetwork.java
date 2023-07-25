package com.example.SunnyWeather.logic.network;

import com.example.SunnyWeather.SunnyWeatherApplication;
import com.example.SunnyWeather.logic.model.DailyResponse;
import com.example.SunnyWeather.logic.model.PlaceResponse;
import com.example.SunnyWeather.logic.model.RealtimeResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*定义一个统一的网络数据源访问入口，对所有网络请求的API进行封装*/
public class SunnyWeatherNetwork {

    private static PlaceService placeService = ServiceCreator.create(PlaceService.class);
    private static WeatherService weatherService = ServiceCreator.create(WeatherService.class);

    //搜索城市地址
    public static Object searchPlaces(String query) throws Throwable {

        Call<PlaceResponse> call = placeService.searchPlaces(query);
        PlaceResponse response = await(call);

        return response;
    }

    public static DailyResponse getDailyWeather(String lng,String lat) throws Throwable {
        Call<DailyResponse> call = weatherService.getDailyWeather(SunnyWeatherApplication.TOKEN,lng, lat);
        DailyResponse response = await(call);
        return response;
    }

    public static RealtimeResponse getRealtimeWeather(String lng,String lat) throws Throwable{
        Call<RealtimeResponse> call = weatherService.getRealtimeWeather(SunnyWeatherApplication.TOKEN,lng, lat);
        RealtimeResponse response = await(call);
        return response;
    }

    public static <T> T await(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            T body = response.body();
            if (body != null) {
                return body;
            } else {
                throw new RuntimeException("响应体为空");
            }
        } else {
            throw new RuntimeException("响应失败：" + response.message());
        }
    }
}
