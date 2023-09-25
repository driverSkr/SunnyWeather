package com.example.sunnyweather_pro.ui.weather;

import static com.example.sunnyweather_pro.logic.utils.WeatherUtils.getSky;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sunnyweather_pro.MyApplication;
import com.example.sunnyweather_pro.R;
import com.example.sunnyweather_pro.logic.dao.PlaceDao;
import com.example.sunnyweather_pro.logic.database.SunnyWeatherDatabase;
import com.example.sunnyweather_pro.logic.model.DailyResponse;
import com.example.sunnyweather_pro.logic.model.PlaceInfo;
import com.example.sunnyweather_pro.logic.model.RealtimeResponse;
import com.example.sunnyweather_pro.logic.model.Sky;
import com.example.sunnyweather_pro.logic.model.Weather;
import com.example.sunnyweather_pro.logic.utils.LinearGradientUtil;
import com.example.sunnyweather_pro.ui.MainActivity;
import com.example.sunnyweather_pro.ui.widget.CircleBarView;
import com.example.sunnyweather_pro.ui.widget.SunUpDown;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressLint("NonConstantResourceId")
public class WeatherFragment extends Fragment implements View.OnClickListener {
    private static PlaceInfo placeInfo;
    private static int num;
    private WeatherViewModel viewModel;
    private final SunnyWeatherDatabase database = MyApplication.getDatabase();
    private final PlaceDao placeDao = database.placeDao();

    private SmartRefreshLayout smartRefresh;
    private TextView placeName,currentTemp,currentSky,currentAQI,coldRiskText, dressingText, ultravioletText,
            carWashingText,humidityText,windText,apparent_temperatureText,comfortText,pm10,pm2_5,no_2,so_2,o_3,co,text_title,text_progress;
    private LinearLayout forecastLayout;
    private RelativeLayout nowLayout;
    private ScrollView weatherLayout;
    private Button navBtn;
    private ImageButton ibt_add;
    private CircleBarView circle_bar;
    private SunUpDown sun_up_down;

    public static WeatherFragment newInstance(PlaceInfo place,int nums) {
        WeatherFragment fragment = new WeatherFragment();
        placeInfo = place;
        num = nums;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        Toast.makeText(MyApplication.context, "当前的城市：" + placeInfo.name, Toast.LENGTH_SHORT).show();
        //初始化绑定组件
        initBinding(view);
        if (num == 1){
            ibt_add.setVisibility(View.VISIBLE);
        }
        //设置刷新事件
        smartRefresh.setOnRefreshListener(refreshLayout -> {
            viewModel.refreshWeather(placeInfo.lng,placeInfo.lat);
            Log.d("boge","name = " + placeInfo.name);
            smartRefresh.finishRefresh();
        });
        //设定点击事件
        addClick();
        //初始化ViewModel
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        //请求天气数据
        viewModel.refreshWeather(placeInfo.lng,placeInfo.lat);

        //观察天气数据的变化
        viewModel.getWeatherLiveData().observe(requireActivity(), weather -> {
            if (weather != null){
                //当获取到服务器返回的天气数据时，就调用showWeatherInfo()方法进行解析与展示
                showWeatherInfo(weather);
            } else {
                Toast.makeText(MyApplication.context, "无法成功获取天气信息", Toast.LENGTH_SHORT).show();
            }
            smartRefresh.autoRefresh();
        });
        return view;
    }

    //数据填充
    @SuppressLint("SetTextI18n")
    private void showWeatherInfo(Weather weather){
        //填充当前天气信息布局（now.xml）
        placeName.setText(placeInfo.name);
        RealtimeResponse.Realtime realtime = weather.realtime;
        Log.d("boge","今日数据" + realtime.toString());
        String currentTempText = String.format(Locale.getDefault(),"%d℃",(int) realtime.temperature);
        currentTemp.setText(currentTempText);
        currentSky.setText(getSky(realtime.skycon).getInfo());
        String currentPM25Text = String.format(Locale.getDefault(),"空气指数 %d",realtime.airQuality.aqi.chn);
        currentAQI.setText(currentPM25Text);
        nowLayout.setBackgroundResource(getSky(realtime.skycon).getBg());

        // 填充未来天气预报布局 (forecast.xml)
        forecastLayout.removeAllViews();
        DailyResponse.Daily daily = weather.daily;
        int days = daily.skycon.size();
        for (int i = 0; i < days; i++) {
            DailyResponse.Skycon skycon = daily.skycon.get(i);
            DailyResponse.Nums temperature = daily.temperature.get(i);
            View view = LayoutInflater.from(requireActivity()).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateInfo = view.findViewById(R.id.dateInfo);
            ImageView skyIcon = view.findViewById(R.id.skyIcon);
            TextView skyInfo = view.findViewById(R.id.skyInfo);
            TextView temperatureInfo = view.findViewById(R.id.temperatureInfo);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            dateInfo.setText(simpleDateFormat.format(skycon.date));
            Sky sky = getSky(skycon.value);
            skyIcon.setImageResource(sky.getIcon());
            skyInfo.setText(sky.getInfo());
            String tempText = String.format(Locale.getDefault(),"%d ~ %d ℃",(int) temperature.min,(int) temperature.max);
            temperatureInfo.setText(tempText);
            forecastLayout.addView(view);
        }

        //填充空气质量(air_quality)
        RealtimeResponse.AirQuality airQuality = realtime.airQuality;
        pm10.setText(airQuality.pm10 + "");
        pm2_5.setText(airQuality.pm25 + "");
        no_2.setText(airQuality.no2 + "");
        so_2.setText(airQuality.so2 + "");
        o_3.setText(airQuality.o3 + "");
        co.setText(airQuality.co + "");
        text_title.setText(airQuality.description.chn + "");
        //初始化圆环进度条
        initCircleBar(airQuality.aqi.chn);


        // 填充生活指数布局 (life_index.xml)
        DailyResponse.LifeIndex lifeIndex = daily.lifeIndex;
        coldRiskText.setText(lifeIndex.coldRisk.get(0).desc);
        dressingText.setText(lifeIndex.dressing.get(0).desc);
        ultravioletText.setText(lifeIndex.ultraviolet.get(0).desc);
        carWashingText.setText(lifeIndex.carWashing.get(0).desc);
        humidityText.setText((realtime.humidity * 100) + "%");
        windText.setText(realtime.wind.speed + "m/s");
        apparent_temperatureText.setText(realtime.apparent_temperature + "℃");
        comfortText.setText(realtime.lifeIndex.comfort.desc);
        weatherLayout.setVisibility(View.VISIBLE);

        //日出日落（sun_rise_set.xml)
        sun_up_down.setProgressNum(3000);
    }

    private void initBinding(View view){
        smartRefresh = view.findViewById(R.id.smartRefresh);
        placeName = view.findViewById(R.id.placeName);
        currentTemp = view.findViewById(R.id.currentTemp);
        currentSky = view.findViewById(R.id.currentSky);
        currentAQI = view.findViewById(R.id.currentAQI);
        coldRiskText = view.findViewById(R.id.coldRiskText);
        dressingText = view.findViewById(R.id.dressingText);
        ultravioletText = view.findViewById(R.id.ultravioletText);
        carWashingText = view.findViewById(R.id.carWashingText);
        humidityText = view.findViewById(R.id.humidityText);
        windText = view.findViewById(R.id.windText);
        apparent_temperatureText = view.findViewById(R.id.apparent_temperatureText);
        comfortText = view.findViewById(R.id.comfortText);
        forecastLayout = view.findViewById(R.id.forecastLayout);
        nowLayout = view.findViewById(R.id.nowLayout);
        weatherLayout = view.findViewById(R.id.weatherLayout);
        navBtn = view.findViewById(R.id.navBtn);
        ibt_add = view.findViewById(R.id.ibt_add);
        circle_bar = view.findViewById(R.id.circle_bar);
        text_progress = view.findViewById(R.id.text_progress);
        text_title = view.findViewById(R.id.text_title);
        pm10 = view.findViewById(R.id.pm10);
        pm2_5 = view.findViewById(R.id.pm2_5);
        no_2 = view.findViewById(R.id.no_2);
        so_2 = view.findViewById(R.id.so_2);
        o_3 = view.findViewById(R.id.o_3);
        co = view.findViewById(R.id.co);
        sun_up_down = view.findViewById(R.id.sun_up_down);
    }

    private void initCircleBar(float progressNum){
        circle_bar.setTextView(text_progress);
        circle_bar.setMaxNum(500);
        circle_bar.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float progressNum, float maxNum) {
                DecimalFormat decimalFormat=new DecimalFormat("0");
                return decimalFormat.format(interpolatedTime * progressNum);
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {
                LinearGradientUtil linearGradientUtil = new LinearGradientUtil(Color.GREEN,Color.GREEN);
                paint.setColor(linearGradientUtil.getColor(interpolatedTime));
            }
        });
        //设置动画时间为3000毫秒，即3秒
        circle_bar.setProgressNum(progressNum,3000);
    }

    private void addClick(){
        ibt_add.setOnClickListener(this);
        navBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navBtn:
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.putExtra("comeback",true);
                startActivity(intent);
                break;
            case R.id.ibt_add:
                //写线程：确保插入、删除操作在后台线程中进行，避免阻塞主线程
                SunnyWeatherDatabase.databaseWriteExecutor.execute(() ->{
                    placeDao.insertOnePlace(placeInfo);
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(MyApplication.context, "保存成功", Toast.LENGTH_SHORT).show());
                });
                ibt_add.setVisibility(View.GONE);
                Intent intent1 = new Intent(requireActivity(), WeatherActivity.class);
                //数据库里有地址，直接展示地址数据
                intent1.putExtra("hasPlace",true);
                startActivity(intent1);
                break;
        }
    }
}