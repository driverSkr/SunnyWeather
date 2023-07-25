package com.example.SunnyWeather.ui.place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.SunnyWeather.logic.Repository;
import com.example.SunnyWeather.logic.model.Location;
import com.example.SunnyWeather.logic.model.Weather;
import com.example.SunnyWeather.logic.utils.Result;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    public String locationLng = "";
    public String locationLat = "";
    public String placeName = "";

    public LiveData<Result<Weather>> getWeatherLiveData(){
        return Transformations.switchMap(locationLiveData,location -> Repository.refreshWeather(location.getLng(),location.getLat()));
    }

    public void refreshWeather(String lng,String lat){
        locationLiveData.setValue(new Location(lng,lat));
    }
}
