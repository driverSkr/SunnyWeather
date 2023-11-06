package com.example.SunnyWeather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

//全局变量设置
public class SunnyWeatherApplication extends Application {

    //获取Context并非是那么容易的一件事，有时候还是挺伤脑筋的,设置全局Context
    //将Context设置成静态变量很容易会产生内存泄漏的问题，所以这是一种有风险的做法
    //但是由于这里获取的不是Activity 或Service 中的Context，而是Application 中的Context，它全局只会存在一份实例，并且在整个应用程序的生命周期内都不会回收，因此是不存在内存泄漏风险的
    @SuppressLint("StaticFieldLeak")//让Android Studio 忽略上述警告提示
    public static Context context = null;
    //彩云天气的开发者平台申请的令牌值
    public static final String TOKEN = "lU0rIYm9Ba1sWHY5";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
