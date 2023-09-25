package com.example.sunnyweather_pro.logic.model;

import java.util.List;

/*城市地址响应数据*/
public class PlaceResponse {
    //响应状态
    public String status;
    //地址数据包
    public List<Place> places;

    public PlaceResponse(String status, List<Place> places) {
        this.status = status;
        this.places = places;
    }
}
