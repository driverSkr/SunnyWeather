package com.example.SunnyWeather.logic;

import android.graphics.Interpolator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.SunnyWeather.logic.dao.PlaceDao;
import com.example.SunnyWeather.logic.model.DailyResponse;
import com.example.SunnyWeather.logic.model.Place;
import com.example.SunnyWeather.logic.model.PlaceResponse;
import com.example.SunnyWeather.logic.model.RealtimeResponse;
import com.example.SunnyWeather.logic.model.Weather;
import com.example.SunnyWeather.logic.network.SunnyWeatherNetwork;
import com.example.SunnyWeather.logic.utils.Result;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kotlinx.coroutines.Dispatchers;

//单例类,作为仓库层的统一封装入口
public class Repository {

    public static void savePlace(Place place){
        PlaceDao.savePlace(place);
    }

    public static Place getSavedPlace() {
        return PlaceDao.getSavedPlace();
    }

    public static boolean isPlaceSaved() {
        return PlaceDao.isPlaceSaved();
    }

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

    public static LiveData<Result<Weather>> refreshWeather(String lng,String lat){
        return fire(() -> {
            try {
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                Future<RealtimeResponse> realtimeFuture = executorService.submit(() -> {
                    try {
                        return SunnyWeatherNetwork.getRealtimeWeather(lng,lat);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                Future<DailyResponse> dailyFuture  = executorService.submit(() -> {
                    try {
                        return SunnyWeatherNetwork.getDailyWeather(lng,lat);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return null;
                    }
                });

                RealtimeResponse realtimeResponse = realtimeFuture.get();
                DailyResponse dailyResponse = dailyFuture.get();

                executorService.shutdown();

                if ("ok".equals(realtimeResponse.getStatus()) && "ok".equals(dailyResponse.getStatus())){
                    Weather weather = new Weather(realtimeResponse.getResult().getRealtime(),dailyResponse.getResult().getDaily());
                    return Result.success(weather);
                }else {
                    return Result.failure(new RuntimeException(
                            "realtime response status is " + realtimeResponse.getStatus() +
                            ", daily response status is " + dailyResponse.getStatus()));
                }
            }catch (Exception e){
                return Result.failure(e);
            }
        });
    }

    private static <T> LiveData<Result<T>> fire(Callable<Result<T>> block){
        MutableLiveData<Result<T>> liveData = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() ->{
            try {
                Result<T> result = block.call();
                liveData.postValue(result);
            }catch (Exception e){
                Result<T> result = Result.failure(e);
                liveData.postValue(result);
            }
        });

        executorService.shutdown();
        return liveData;
    }
}
