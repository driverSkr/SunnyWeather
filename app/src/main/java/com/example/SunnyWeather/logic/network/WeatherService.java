package com.example.SunnyWeather.logic.network;

import com.example.SunnyWeather.logic.model.DailyResponse;
import com.example.SunnyWeather.logic.model.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    //用于获取实时的天气信息
        //@Path注解来向请求接口中动态传入经纬度的坐标
    @GET("v2.5/{TOKEN}/{lng},{lat}/realtime.json")
    Call<RealtimeResponse> getRealtimeWeather(@Path("TOKEN") String token,
                                              @Path("lng") String lng,
                                              @Path("lat") String lat);

    //用于获取未来的天气信息
    @GET("v2.5/{TOKEN}/{lng},{lat}/daily.json")
    Call<DailyResponse> getDailyWeather(@Path("TOKEN") String token,
                                        @Path("lng") String lng,
                                        @Path("lat") String lat);
}
