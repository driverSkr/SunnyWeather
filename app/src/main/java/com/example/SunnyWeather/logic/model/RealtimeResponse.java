package com.example.SunnyWeather.logic.model;

import com.google.gson.annotations.SerializedName;

public class RealtimeResponse {
    private String status;
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

    public static class Result {
        private Realtime realtime;

        public Result(Realtime realtime) {
            this.realtime = realtime;
        }

        public Realtime getRealtime() {
            return realtime;
        }
    }

    public static class Realtime {
        private String skycon;
        private float temperature;

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

    public static class AirQuality {
        private AQI aqi;

        public AirQuality(AQI aqi) {
            this.aqi = aqi;
        }

        public AQI getAqi() {
            return aqi;
        }
    }

    public static class AQI {
        private float chn;

        public AQI(float chn) {
            this.chn = chn;
        }

        public float getChn() {
            return chn;
        }
    }
}
