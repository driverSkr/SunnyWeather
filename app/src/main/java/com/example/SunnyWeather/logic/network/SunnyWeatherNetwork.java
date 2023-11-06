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

    //创建Service接口的动态代理对象
    private static PlaceService placeService = ServiceCreator.create(PlaceService.class);
    private static WeatherService weatherService = ServiceCreator.create(WeatherService.class);

    //搜索城市地址
    public static Object searchPlaces(String query) throws Throwable {
        //该方法是一个异步请求，用于搜索与查询字符串 query 相关的城市地址
        Call<PlaceResponse> call = placeService.searchPlaces(query);
        //通过 await 方法等待网络请求的结果，将 PlaceResponse 对象作为结果返回
        PlaceResponse response = await(call);

        return response;
    }
    //用于获取每日天气数据
    public static DailyResponse getDailyWeather(String lng,String lat) throws Throwable {
        //getDailyWeather方法也是一个异步请求，用于获取指定经度 lng 和纬度 lat 处的每日天气信息
        Call<DailyResponse> call = weatherService.getDailyWeather(SunnyWeatherApplication.TOKEN,lng, lat);
        //await 方法等待网络请求的结果
        DailyResponse response = await(call);
        return response;
    }
    //获取实时天气数据
    public static RealtimeResponse getRealtimeWeather(String lng,String lat) throws Throwable{
        Call<RealtimeResponse> call = weatherService.getRealtimeWeather(SunnyWeatherApplication.TOKEN,lng, lat);
        RealtimeResponse response = await(call);
        return response;
    }
    //用于执行网络请求，提供了同步获取响应数据的方法，并等待结果的返回
    //接受一个 Retrofit 的 Call 对象作为参数
    public static <T> T await(Call<T> call) throws IOException {
        //使用 execute() 方法来同步执行网络请求
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            T body = response.body();
            if (body != null) {
                //如果请求成功且响应体不为空，则将结果返回
                return body;
            } else {
                //否则，抛出运行时异常，提示响应体为空
                throw new RuntimeException("响应体为空");
            }
        } else {
            //请求失败，则提示响应失败
            throw new RuntimeException("响应失败：" + response.message());
        }
    }
}
