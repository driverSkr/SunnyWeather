package com.example.sunnyweather_pro.logic.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//地址信息。用于生成数据库对应的表
@Entity(tableName = "PlaceInfo")
public class PlaceInfo {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    //经度 longitude
    public String lng;
    //经度 longitude
    public String lat;

    public PlaceInfo(@NonNull String id, String name, String lng, String lat) {
        this.id = id;
        this.name = name;
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
