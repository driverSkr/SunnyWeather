package com.example.sunnyweather_pro.logic.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sunnyweather_pro.logic.dao.PlaceDao;
import com.example.sunnyweather_pro.logic.model.PlaceInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//entities表示该数据库有哪些表，version表示数据库的版本号
//exportSchema表示是否导出数据库信息的json串，建议设为false，
//              若设为true还需指定json文件的保存路径(写在了Module的build.gradle的defaultConfig{}里)
@Database(entities = {PlaceInfo.class},version = 1,exportSchema = false)
public abstract class SunnyWeatherDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // 获取该数据库中某张表的持久化对象
    public abstract PlaceDao placeDao();
}
