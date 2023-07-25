package com.example.SunnyWeather.ui.place;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.SunnyWeather.logic.Repository;
import com.example.SunnyWeather.logic.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {

    // 创建一个可变的LiveData对象来存储搜索关键词
    private MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    // 创建一个包含Place对象的ArrayList用于存储地点列表
    private ArrayList<Place> placeList = new ArrayList<>();

    public ArrayList<Place> getPlaceList() {
        return placeList;
    }

    //// 创建一个LiveData对象来监听搜索关键词的变化，并调用Repository的searchPlaces方法进行搜索
    private LiveData<List<Place>> placeLiveData = Transformations.switchMap(searchLiveData,query ->{
        return new Repository().searchPlaces(query);
    });

    public LiveData<List<Place>> getPlaceLiveData(){
        return placeLiveData;
    }

    //用于执行地点搜索的方法，通过改变searchLiveData的值来触发搜索
    public void searchPlaces(String query){
        searchLiveData.setValue(query);
    }

    public void savePlace(Place place) {
        Repository.savePlace(place);
    }

    public Place getSavedPlace() {
        return Repository.getSavedPlace();
    }

    public boolean isPlaceSaved() {
        return Repository.isPlaceSaved();
    }
}
