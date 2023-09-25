package com.example.sunnyweather_pro.logic.network;

import com.example.sunnyweather_pro.MyApplication;
import com.example.sunnyweather_pro.logic.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//首先定义一个用于访问彩云天气 城市搜索API的Retrofit接口
public interface PlaceService {

    String BASE_UEL = "v2/place?token=" + MyApplication.TOKEN + "&lang=zh_CN";
    /*搜索城市数据的API中只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现，
    另外两个参数是不会变的，因此固定写在@GET注解中即可*/
    //返回值被声明成了Call<PlaceResponse>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象了
    @GET(BASE_UEL)
    Call<PlaceResponse> searchPlaces(@Query("query") String query);
}
