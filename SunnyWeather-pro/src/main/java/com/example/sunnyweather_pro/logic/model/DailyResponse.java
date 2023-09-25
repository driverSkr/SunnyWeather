package com.example.sunnyweather_pro.logic.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

//未来几天的天气数据
public class DailyResponse {
    public String status;
    public Result result;

    public static class Result{
        public Daily daily;
    }

    public static class Daily{
        public ArrayList<Astro> astro;//天文数据
        public ArrayList<Nums> precipitation;//降水量
        public ArrayList<Nums> temperature;//温度
        public ArrayList<Wind> wind;//风力
        public ArrayList<Nums> humidity;//湿度
        public ArrayList<Nums> cloudrate;//云量
        public ArrayList<Nums> pressure;//气压
        public ArrayList<Nums> visibility;//能见度
        public ArrayList<Nums> dswrf;//直接太阳辐射
        @SerializedName("air_quality")
        public AirQuality airQuality;
        public ArrayList<Skycon> skycon;//天气状况
        @SerializedName("life_index")
        public LifeIndex lifeIndex;

        @NonNull
        @Override
        public String toString() {
            return "Daily{" +
                    "astro=" + astro +
                    ", precipitation=" + precipitation +
                    ", temperature=" + temperature +
                    ", wind=" + wind +
                    ", humidity=" + humidity +
                    ", cloudrate=" + cloudrate +
                    ", pressure=" + pressure +
                    ", visibility=" + visibility +
                    ", dswrf=" + dswrf +
                    ", airQuality=" + airQuality +
                    ", skycon=" + skycon +
                    ", lifeIndex=" + lifeIndex +
                    '}';
        }
    }

    public static class Astro{
        public Time sunrise;
        public Time sunset;

        static class Time{
            public String time;
        }
    }

    public static class Nums{
        public float max;
        public float min;
        public float avg;
    }

    public static class Wind{
        public Num max;
        public Num min;
        public Num avg;
        static class Num{
            float speed;
            float direction;
        }
    }

    public static class AirQuality{
        public ArrayList<Aqi> aqi;
        public ArrayList<Nums> pm25;
        static class Aqi{
            public ChnUsa max;
            public ChnUsa avg;
            public ChnUsa min;
            static class ChnUsa{
                int chn;
                int usa;
            }
        }
    }

    public static class Skycon{
        //时间
        public Date date;
        public String value;
    }

    public static class LifeIndex{
        public ArrayList<Message> ultraviolet;//紫外线
        public ArrayList<Message> carWashing;//洗车
        public ArrayList<Message> dressing;//穿衣
        public ArrayList<Message> comfort;//舒适度
        public ArrayList<Message> coldRisk;//感冒风险
        public static class Message{
            public String index;
            public String desc;
        }
    }
}
