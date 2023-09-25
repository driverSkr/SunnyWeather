package com.example.sunnyweather_pro.logic.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

//实时天气信息响应
public class RealtimeResponse {
    //响应状态
    public String status;
    //结果数据
    public Result result;

    public static class Result{
        public Realtime realtime;
    }

    public static class Realtime{
        public float temperature;//温度
        public float humidity;//湿度
        public float cloudrate;//
        public String skycon;//天气状况
        public float visibility;//能见度
        public float dswrf;//直接太阳辐射
        public Wind wind;//风力
        public float pressure;//气压
        public float apparent_temperature;//体感温度
        @SerializedName("air_quality")
        public AirQuality airQuality;//空气质量
        @SerializedName("life_index")
        public LifeIndex lifeIndex;//生活指数

        @NonNull
        @Override
        public String toString() {
            return "Realtime{" +
                    "temperature=" + temperature +
                    ", humidity=" + humidity +
                    ", cloudrate=" + cloudrate +
                    ", skycon='" + skycon + '\'' +
                    ", visibility=" + visibility +
                    ", dswrf=" + dswrf +
                    ", wind=" + wind +
                    ", pressure=" + pressure +
                    ", apparent_temperature=" + apparent_temperature +
                    ", airQuality=" + airQuality +
                    ", lifeIndex=" + lifeIndex +
                    '}';
        }
    }

    public static class Wind{
        public float speed;
        public float direction;
    }

    public static class AirQuality{
        public int pm25;
        public int pm10;
        public int o3;
        public int so2;
        public int no2;
        public float co;
        public Aqi aqi;
        public Description description;

        public static class Aqi{
            public int chn;
            public int usa;

            @Override
            public String toString() {
                return "Aqi{" +
                        "chn=" + chn +
                        ", usa=" + usa +
                        '}';
            }
        }

        public static class Description{
            public String chn;
            public String usa;

            @Override
            public String toString() {
                return "Description{" +
                        "chn='" + chn + '\'' +
                        ", usa='" + usa + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "AirQuality{" +
                    "pm25=" + pm25 +
                    ", pm10=" + pm10 +
                    ", o3=" + o3 +
                    ", so2=" + so2 +
                    ", no2=" + no2 +
                    ", co=" + co +
                    ", aqi=" + aqi +
                    ", description=" + description +
                    '}';
        }
    }

    public static class LifeIndex{
        public Ultraviolet ultraviolet;//紫外线√
        public Comfort comfort;//舒适度√

        static class Ultraviolet{
            float index;
            String desc;
        }

        public static class Comfort{
            public float index;
            public String desc;
        }
    }
}
