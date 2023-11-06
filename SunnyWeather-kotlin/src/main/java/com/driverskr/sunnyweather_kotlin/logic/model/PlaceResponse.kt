package com.driverskr.sunnyweather_kotlin.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: driverSkr
 * @Time: 2023/11/6 15:06
 * @Description: 地址相关数据$
 */
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
                @SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)
