package com.example.sunnyweather_pro.logic;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather_pro.MyApplication;
import com.example.sunnyweather_pro.logic.model.DailyResponse;
import com.example.sunnyweather_pro.logic.model.Place;
import com.example.sunnyweather_pro.logic.model.PlaceResponse;
import com.example.sunnyweather_pro.logic.model.RealtimeResponse;
import com.example.sunnyweather_pro.logic.model.Weather;
import com.example.sunnyweather_pro.logic.network.SunnyWeatherNetwork;

import java.util.List;

//单例类,作为仓库层的统一封装入口(把需要开启线程的操作放在了这里)
/*主要工作就是判断调用方请求的数据应该是从本地数据源中获取还是从网络数据源中获取，并将获得的数据返回给调用方
。因此，仓库层有点像是一个数据获取与缓存的中间层，在本地没有缓存数据的情况下就去网络层获取，如果本地已经有缓存了，就直接将缓存数据返回*/
public class Repository {
    //用于搜索相关的城市数据
    //使用 LiveData 实现数据的观察和通知，方便在UI层进行响应
    public LiveData<List<Place>> searchPlaces(String query){
        //创建了一个可变的 LiveData 对象，用于存储搜索结果并在后续进行数据更新通知
        MutableLiveData<List<Place>> liveData = new MutableLiveData<>();

        PlaceResponse placeResponse = SunnyWeatherNetwork.searchPlaces(query);
        if (placeResponse != null && placeResponse.status.equals("ok")){
            //从 placeResponse 中获取城市数据列表
            List<Place> places = placeResponse.places;
            //将获取到的城市数据列表 places 通过 liveData 的 postValue 方法进行更新，通知观察者（通常是UI层）数据已更新
            liveData.postValue(places);
        } else {
            //将空值（null）通过 liveData 的 postValue 方法进行更新，同样通知观察者数据已更新，但此时数据为空
            liveData.postValue(null);
        }
        //返回存储搜索结果的 LiveData 对象 liveData，允许调用方观察搜索结果并进行相应处理
        return liveData;
    }

    //接受经度 lng 和纬度 lat 作为参数，用于获取对应地点的实时天气数据和每日天气数据
    public static LiveData<Weather> refreshWeather(String lng,String lat){
        //创建了一个可变的 LiveData 对象，用于存储搜索结果并在后续进行数据更新通知
        MutableLiveData<Weather> liveData = new MutableLiveData<>();

        RealtimeResponse realtimeWeather = SunnyWeatherNetwork.getRealtimeWeather(lng, lat);
        DailyResponse dailyWeather = SunnyWeatherNetwork.getDailyWeather(lng, lat);
        //检查实时天气数据和每日天气数据的请求是否都成功
        if (realtimeWeather != null && dailyWeather != null){
            if ("ok".equals(realtimeWeather.status) && "ok".equals(dailyWeather.status)){
                //如果数据请求成功，通过 realtimeResponse 和 dailyResponse 分别获取实时天气数据和每日天气数据，并使用这些数据创建一个 Weather 对象
                Weather weather = new Weather(realtimeWeather.result.realtime, dailyWeather.result.daily);
                liveData.postValue(weather);
            } else {
                liveData.postValue(null);
                Toast.makeText(MyApplication.context, "实时天气状态：" + realtimeWeather.status + " ;每日天气状态：" + dailyWeather.status, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MyApplication.context, "获取数据失败", Toast.LENGTH_SHORT).show();
            liveData.postValue(null);
        }
        return liveData;
    }
}
