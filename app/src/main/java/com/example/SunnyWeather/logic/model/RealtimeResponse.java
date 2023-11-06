package com.example.SunnyWeather.logic.model;

import com.google.gson.annotations.SerializedName;

//实时天气信息响应
public class RealtimeResponse {
    //响应状态
    private String status;
    //结果数据
    private Result result;

    public RealtimeResponse(String status, Result result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public Result getResult() {
        return result;
    }
    //结果
    public static class Result {
        //实时
        private Realtime realtime;

        public Result(Realtime realtime) {
            this.realtime = realtime;
        }

        public Realtime getRealtime() {
            return realtime;
        }
    }
    //实时天气
    public static class Realtime {
        //天气类型（阴、小雨、大风等）
        private String skycon;
        //温度
        private float temperature;
        //空气质量
        @SerializedName("air_quality")
        private AirQuality airQuality;

        public Realtime(String skycon, float temperature, AirQuality airQuality) {
            this.skycon = skycon;
            this.temperature = temperature;
            this.airQuality = airQuality;
        }

        public String getSkycon() {
            return skycon;
        }

        public float getTemperature() {
            return temperature;
        }

        public AirQuality getAirQuality() {
            return airQuality;
        }
    }
    //空气质量
    public static class AirQuality {
        //空气质量指数 Air Quality Index
        private AQI aqi;

        public AirQuality(AQI aqi) {
            this.aqi = aqi;
        }

        public AQI getAqi() {
            return aqi;
        }
    }
    //空气质量指数
    public static class AQI {
        //chn:指中国地区
        private float chn;

        public AQI(float chn) {
            this.chn = chn;
        }

        public float getChn() {
            return chn;
        }
    }
}
