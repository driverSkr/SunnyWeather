package com.example.SunnyWeather.logic.utils;

import androidx.annotation.DrawableRes;

import com.example.SunnyWeather.R;
import com.example.SunnyWeather.logic.model.Sky;

import java.util.HashMap;
import java.util.Map;

//转换函数。
// 因为彩云天气返回的数据中，天气情况都是一些诸如CLOUDY、WIND之类的天气代码，我们需要编写一个转换函数将这些天气代码转换成一个Sky对象
public class WeatherUtils {
    private static final Sky CLEAR_DAY = new Sky("晴", R.drawable.ic_clear_day, R.drawable.bg_clear_day);
    private static final Sky CLEAR_NIGHT = new Sky("晴", R.drawable.ic_clear_night, R.drawable.bg_clear_night);
    private static final Sky PARTLY_CLOUDY_DAY = new Sky("多云", R.drawable.ic_partly_cloud_day, R.drawable.bg_partly_cloudy_day);
    private static final Sky PARTLY_CLOUDY_NIGHT = new Sky("多云", R.drawable.ic_partly_cloud_night, R.drawable.bg_partly_cloudy_night);
    private static final Sky CLOUDY = new Sky("阴", R.drawable.ic_cloudy, R.drawable.bg_cloudy);
    private static final Sky WIND = new Sky("大风", R.drawable.ic_cloudy, R.drawable.bg_wind);
    private static final Sky LIGHT_RAIN = new Sky("小雨", R.drawable.ic_light_rain, R.drawable.bg_rain);
    // Add other sky objects here

    private static final Map<String, Sky> skyMap = new HashMap<>();

    static {
        skyMap.put("CLEAR_DAY", CLEAR_DAY);
        skyMap.put("CLEAR_NIGHT", CLEAR_NIGHT);
        skyMap.put("PARTLY_CLOUDY_DAY", PARTLY_CLOUDY_DAY);
        skyMap.put("PARTLY_CLOUDY_NIGHT", PARTLY_CLOUDY_NIGHT);
        skyMap.put("CLOUDY", CLOUDY);
        skyMap.put("WIND", WIND);
        skyMap.put("LIGHT_RAIN", LIGHT_RAIN);
        // Add other sky mappings here
    }

    public static Sky getSky(String skycon) {
        Sky sky = skyMap.get(skycon);
        return sky != null ? sky : skyMap.get("CLEAR_DAY");
    }
}

