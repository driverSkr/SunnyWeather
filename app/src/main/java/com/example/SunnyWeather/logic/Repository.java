package com.example.SunnyWeather.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.SunnyWeather.logic.model.Place;
import com.example.SunnyWeather.logic.model.PlaceResponse;
import com.example.SunnyWeather.logic.network.SunnyWeatherNetwork;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kotlinx.coroutines.Dispatchers;

//单例类,作为仓库层的统一封装入口
public class Repository {

    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<Place>> searchPlaces(String query){
        MutableLiveData<List<Place>> liveData = new MutableLiveData<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlaceResponse placeResponse = (PlaceResponse) SunnyWeatherNetwork.searchPlaces(query);
                    if (placeResponse.getStatus().equals("ok")){
                        List<Place> places = placeResponse.getPlaces();
                        liveData.postValue(places);
                    }else {
                        liveData.postValue(null);
                    }
                }catch (Throwable e){
                    liveData.postValue(null);
                }
            }
        });
        return liveData;
    }
}
