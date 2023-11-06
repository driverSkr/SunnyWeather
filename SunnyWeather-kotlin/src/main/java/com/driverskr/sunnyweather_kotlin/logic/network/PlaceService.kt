package com.driverskr.sunnyweather_kotlin.logic.network

import com.driverskr.sunnyweather_kotlin.SunnyWeatherApplication
import com.driverskr.sunnyweather_kotlin.logic.model.PlaceResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: driverSkr
 * @Time: 2023/11/6 15:10
 * @Description: 城市搜索接口$
 */
interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}