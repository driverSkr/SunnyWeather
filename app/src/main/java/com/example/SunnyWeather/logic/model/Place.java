package com.example.SunnyWeather.logic.model;

import com.google.gson.annotations.SerializedName;

public class Place {
    //城市名
    private String name;
    //定位（经纬度）
    private Location location;
    //详细地址
    @SerializedName("formatted_address")//让JSON字段和Kotlin 字段之间建立映射关系
    private String address;

    public Place(String name, Location location, String address) {
        this.name = name;
        this.location = location;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
