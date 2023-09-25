package com.example.sunnyweather_pro.logic.model;

import com.google.gson.annotations.SerializedName;

//城市地址
public class Place {

    public String id;
    //城市名
    public String name;
    //详细地址
    @SerializedName("formatted_address")//让JSON字段和Java字段之间建立映射关系
    public final String address;
    //定位（经纬度）
    public Location location;

    public Place(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static class Location {
        //经度 longitude
        public String lng;
        //经度 longitude
        public String lat;

        public Location(String lng, String lat) {
            this.lng = lng;
            this.lat = lat;
        }
    }
}
