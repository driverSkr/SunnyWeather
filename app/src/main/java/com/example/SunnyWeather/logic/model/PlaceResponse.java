package com.example.SunnyWeather.logic.model;

import java.util.List;

public class PlaceResponse {
    //响应状态
    private String status;
    //地址数据包
    private List<Place> places;

    public PlaceResponse(String status, List<Place> places) {
        this.status = status;
        this.places = places;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
