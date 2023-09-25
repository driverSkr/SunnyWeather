package com.example.sunnyweather_pro.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather_pro.MyApplication;
import com.example.sunnyweather_pro.logic.Repository;
import com.example.sunnyweather_pro.logic.model.Place;
import com.example.sunnyweather_pro.logic.model.PlaceInfo;
import com.example.sunnyweather_pro.logic.model.Weather;

import java.util.List;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<Place.Location> locationLiveData = new MutableLiveData<>();

    //监听数据库里的数据
    private final LiveData<List<PlaceInfo>> placeInfoList = MyApplication.getDatabase().placeDao().queryAllPlace();

    public LiveData<List<PlaceInfo>> getPlaceInfoList(){
        return placeInfoList;
    }

    public LiveData<Weather> getWeatherLiveData(){
        return Transformations.switchMap(locationLiveData,location ->
                Repository.refreshWeather(location.lng,location.lat));
    }

    public void refreshWeather(String lng,String lat){
        locationLiveData.setValue(new Place.Location(lng,lat));
    }
}
