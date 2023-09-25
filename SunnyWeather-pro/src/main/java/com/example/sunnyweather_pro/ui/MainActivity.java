package com.example.sunnyweather_pro.ui;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sunnyweather_pro.R;
import com.example.sunnyweather_pro.ui.weather.WeatherActivity;
import com.example.sunnyweather_pro.ui.weather.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    private boolean comeback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //让背景图和状态栏融合到一起（非借助Material库完成）
        View decorView = getWindow().getDecorView();//拿到当前Activity的DecorView
        //表示Activity 的布局会显示在状态栏上面
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        //将状态栏设置成透明色
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent getIntent = getIntent();
        Bundle extras = getIntent.getExtras();
        if (extras != null){
            comeback = extras.getBoolean("comeback");
        } else comeback = false;

        WeatherViewModel viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getPlaceInfoList().observe(this, placeInfos -> {
            if (!placeInfos.isEmpty() && !comeback){
                Intent intent = new Intent(this, WeatherActivity.class);
                //数据库里有地址，直接展示地址数据
                intent.putExtra("hasPlace",true);
                startActivity(intent);
            }
        });
    }
}
