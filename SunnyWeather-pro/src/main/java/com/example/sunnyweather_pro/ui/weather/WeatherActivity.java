package com.example.sunnyweather_pro.ui.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sunnyweather_pro.R;
import com.example.sunnyweather_pro.logic.model.PlaceInfo;
import com.example.sunnyweather_pro.util.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class WeatherActivity extends AppCompatActivity {

    private ViewPager vp_content;
    private RadioGroup rg_tab;
    private WeatherViewModel viewModel;
    private List<PlaceInfo> placeInfoList;
    private WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_weather);
//-----------------------------------------------------------------------------------------
        vp_content = findViewById(R.id.vp_content);
        rg_tab = findViewById(R.id.rg_tab);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        intentInfo();
        adapter = new WeatherAdapter(getSupportFragmentManager(),placeInfoList);

        // 设置二代翻页视图的排列方向为水平方向
        vp_content.setAdapter(adapter);// 设置二代翻页视图的适配器
    }

    //根据保存的地址数量生成对应数量的RadioButton
    private void viewBinding(){
        int dip_15 = Utils.dip2px(this, 15);
        for (int i = 0; i < placeInfoList.size(); i++) {
            RadioButton radio = new RadioButton(this); // 创建一个单选按钮对象
            radio.setLayoutParams(new RadioGroup.LayoutParams(dip_15, dip_15));
            radio.setButtonDrawable(R.drawable.selector_circle);
            rg_tab.addView(radio);
        }
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (rg_tab.getChildCount() > 0){
                    ((RadioButton)rg_tab.getChildAt(position)).setChecked(true);
                }
            }
            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        vp_content.setCurrentItem(0);
    }

    //处理Intent信息
    private void intentInfo(){
        placeInfoList = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (!extras.getBoolean("hasPlace")){
            String location_lng = extras.getString("location_lng");
            String location_lat = extras.getString("location_lat");
            String place_name = extras.getString("place_name");
            String place_id = extras.getString("place_id");
            placeInfoList.add(new PlaceInfo(place_id,place_name,location_lng,location_lat));

        } else {
            viewModel.getPlaceInfoList().observe(this, placeInfos -> {
                placeInfoList.clear();
                placeInfoList.addAll(placeInfos);
                Log.d("boge","获得到的地址列表：" + placeInfoList.toString());
                adapter.notifyDataSetChanged();
                viewBinding();
            });
        }
    }
}