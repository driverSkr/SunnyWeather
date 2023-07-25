package com.example.SunnyWeather;

import static com.example.SunnyWeather.logic.utils.WeatherUtils.getSky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SunnyWeather.logic.model.DailyResponse;
import com.example.SunnyWeather.logic.model.RealtimeResponse;
import com.example.SunnyWeather.logic.model.Sky;
import com.example.SunnyWeather.logic.model.Weather;
import com.example.SunnyWeather.logic.utils.Result;
import com.example.SunnyWeather.logic.utils.WeatherUtils;
import com.example.SunnyWeather.ui.place.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;
    private TextView placeName,currentTemp,currentSky,currentAQI,coldRiskText, dressingText, ultravioletText, carWashingText;
    private LinearLayout forecastLayout;
    private RelativeLayout nowLayout;
    private ScrollView weatherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //让背景图和状态栏融合到一起（非借助Material库完成）
        View decorView = getWindow().getDecorView();//拿到当前Activity的DecorView
        //表示Activity 的布局会显示在状态栏上面
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        //将状态栏设置成透明色
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_weather);

        //初始化ViewModel
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // 初始化视图元素
        placeName = findViewById(R.id.placeName);
        currentTemp = findViewById(R.id.currentTemp);
        currentSky = findViewById(R.id.currentSky);
        currentAQI = findViewById(R.id.currentAQI);
        coldRiskText = findViewById(R.id.coldRiskText);
        dressingText = findViewById(R.id.dressingText);
        ultravioletText = findViewById(R.id.ultravioletText);
        carWashingText = findViewById(R.id.carWashingText);
        forecastLayout = findViewById(R.id.forecastLayout);
        nowLayout = findViewById(R.id.nowLayout);
        weatherLayout = findViewById(R.id.weatherLayout);

        // 检查并获取传递的位置信息
        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng = getIntent().getStringExtra("location_lng");
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat = getIntent().getStringExtra("location_lat");
        }
        if (viewModel.placeName.isEmpty()){
            viewModel.placeName = getIntent().getStringExtra("place_name");
        }

        //观察天气数据的变化
        viewModel.getWeatherLiveData().observe(this, new Observer<Result<Weather>>() {
            @Override
            public void onChanged(Result<Weather> result) {
                Weather weather = result.getOrNull();
                if (weather != null) {
                    showWeatherInfo(weather);
                } else {
                    Toast.makeText(WeatherActivity.this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show();
                    result.getExceptionOrNull().printStackTrace();
                }
            }
        });

        //请求天气数据
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat);
    }

    private void showWeatherInfo(Weather weather){
        //填充当前天气信息布局（now.xml）
        placeName.setText(viewModel.placeName);
        RealtimeResponse.Realtime realtime = weather.getRealtime();
        String currentTempText = String.format(Locale.getDefault(),"%d ℃",(int) realtime.getTemperature());
        currentTemp.setText(currentTempText);
        currentSky.setText(getSky(realtime.getSkycon()).getInfo());
        String currentPM25Text = String.format(Locale.getDefault(),"空气指数 %d",(int) realtime.getAirQuality().getAqi().getChn());
        currentAQI.setText(currentPM25Text);
        nowLayout.setBackgroundResource(getSky(realtime.getSkycon()).getBg());

        // 填充未来天气预报布局 (forecast.xml)
        forecastLayout.removeAllViews();
        DailyResponse.Daily daily = weather.getDaily();
        int days = daily.getSkycon().size();
        for (int i = 0; i < days; i++) {
            DailyResponse.Skycon skycon = daily.getSkycon().get(i);
            DailyResponse.Temperature temperature = daily.getTemperature().get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateInfo = view.findViewById(R.id.dateInfo);
            ImageView skyIcon = view.findViewById(R.id.skyIcon);
            TextView skyInfo = view.findViewById(R.id.skyInfo);
            TextView temperatureInfo = view.findViewById(R.id.temperatureInfo);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateInfo.setText(simpleDateFormat.format(skycon.getDate()));
            Sky sky = getSky(skycon.getValue());
            skyIcon.setImageResource(sky.getIcon());
            skyInfo.setText(sky.getInfo());
            String tempText = String.format(Locale.getDefault(), "%d ~ %d ℃", (int) temperature.getMin(), (int) temperature.getMax());
            temperatureInfo.setText(tempText);
            forecastLayout.addView(view);
        }

        // 填充生活指数布局 (life_index.xml)
        DailyResponse.LifeIndex lifeIndex = daily.getLifeIndex();
        coldRiskText.setText(lifeIndex.getColdRisk().get(0).getDesc());
        dressingText.setText(lifeIndex.getDressing().get(0).getDesc());
        ultravioletText.setText(lifeIndex.getUltraviolet().get(0).getDesc());
        carWashingText.setText(lifeIndex.getCarWashing().get(0).getDesc());
        weatherLayout.setVisibility(View.VISIBLE);
    }
}