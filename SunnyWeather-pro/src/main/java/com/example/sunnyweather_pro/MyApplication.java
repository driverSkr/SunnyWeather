package com.example.sunnyweather_pro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.Window;

import androidx.room.Room;

import com.example.sunnyweather_pro.logic.database.SunnyWeatherDatabase;

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context = null;

    //彩云天气的开发者平台申请的令牌值
    public static final String TOKEN = "lU0rIYm9Ba1sWHY5";

    //声明一个阳光天气数据库对象
    private static SunnyWeatherDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //构建书籍数据库实例（name:数据库里表的名字）
        database = Room.databaseBuilder(this,SunnyWeatherDatabase.class,"PlaceInfo")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                .build();
    }

    //获得数据库实例
    public static SunnyWeatherDatabase getDatabase(){
        return database;
    }
}
