package com.example.sunnyweather_pro.logic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sunnyweather_pro.logic.model.Place;
import com.example.sunnyweather_pro.logic.model.PlaceInfo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlaceDao {

    //加载所有地址信息
    @Query("SELECT * FROM PlaceInfo")
    LiveData<List<PlaceInfo>> queryAllPlace();

    //插入一条地址信息
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 记录重复时替换原记录
    void insertOnePlace(PlaceInfo place);

    //删除指定地址
    @Delete
    void deletePlace(PlaceInfo place);
}
