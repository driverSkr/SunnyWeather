package com.example.sunnyweather_pro.logic.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sunnyweather_pro.MyApplication;

@SuppressLint("CommitPrefEdits")
public class SharedPreferenceDao {

    private static final SharedPreferences shared = MyApplication.context.getSharedPreferences("Place", Context.MODE_PRIVATE);

    public static void save(String name){
        // 获得编辑器的对象
        SharedPreferences.Editor edit = shared.edit();
        // 添加一个名为placeName的字符串参数
        edit.putString("placeName",name);
        // 提交编辑器中的修改
        edit.apply();
    }

    public static String get(String name){
        return shared.getString(name, "深圳");
    }
}
