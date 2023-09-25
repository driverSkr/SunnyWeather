package com.example.sunnyweather_pro.ui.place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather_pro.logic.Repository;
import com.example.sunnyweather_pro.logic.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {

    // 创建一个可变的LiveData对象来存储搜索关键词
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    //用于执行地点搜索的方法，通过改变searchLiveData的值来触发搜索
    public void searchPlaces(String query){
        searchLiveData.setValue(query);
    }

    // 创建一个包含Place对象的ArrayList用于存储地点列表
    /*用于对界面上显示的城市数据进行缓存，因为原则上与界面相关的数据都应该放到ViewModel中
     * 这样可以保证它们在手机屏幕发生旋转的时候不会丢失，稍后我们会在编写UI层代码的时候用到这个集合*/
    private ArrayList<Place> placeList = new ArrayList<>();

    public ArrayList<Place> getPlaceList() {
        return placeList;
    }

    // 创建一个LiveData对象来监听搜索关键词的变化，并调用Repository的searchPlaces方法进行搜索
    private LiveData<List<Place>> placesLiveData = Transformations.switchMap(searchLiveData, query -> new Repository().searchPlaces(query));
    public LiveData<List<Place>> getPlacesLiveData(){
        return placesLiveData;
    }


}
