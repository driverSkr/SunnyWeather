package com.example.SunnyWeather.logic.network;

import com.example.SunnyWeather.logic.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*定义一个统一的网络数据源访问入口，对所有网络请求的API进行封装*/
public class SunnyWeatherNetwork {

    private static PlaceService placeService = ServiceCreator.create(PlaceService.class);

    //搜索城市地址
    public static Object searchPlaces(String query) throws Throwable {

        Call<PlaceResponse> call = placeService.searchPlaces(query);
        PlaceResponse response = await(call);

        return response;
    }

    public static <T> T await(Call<T> call) throws Throwable {
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
