package com.example.SunnyWeather.logic.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class DailyResponse {
    private String status;
    private Result result;

    public DailyResponse(String status, Result result) {
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
        private Daily daily;

        public Result(Daily daily) {
            this.daily = daily;
        }

        public Daily getDaily() {
            return daily;
        }
    }

    public static class Daily {
        private List<Temperature> temperature;
        private List<Skycon> skycon;

        @SerializedName("life_index")
        private LifeIndex lifeIndex;

        public Daily(List<Temperature> temperature, List<Skycon> skycon, LifeIndex lifeIndex) {
            this.temperature = temperature;
            this.skycon = skycon;
            this.lifeIndex = lifeIndex;
        }

        public List<Temperature> getTemperature() {
            return temperature;
        }

        public List<Skycon> getSkycon() {
            return skycon;
        }

        public LifeIndex getLifeIndex() {
            return lifeIndex;
        }
    }

    public static class Temperature {
        private float max;
        private float min;

        public Temperature(float max, float min) {
            this.max = max;
            this.min = min;
        }

        public float getMax() {
            return max;
        }

        public float getMin() {
            return min;
        }
    }

    public static class Skycon {
        private String value;
        private Date date;

        public Skycon(String value, Date date) {
            this.value = value;
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public Date getDate() {
            return date;
        }
    }

    public static class LifeIndex {
        private List<LifeDescription> coldRisk;
        private List<LifeDescription> carWashing;
        private List<LifeDescription> ultraviolet;
        private List<LifeDescription> dressing;

        public LifeIndex(List<LifeDescription> coldRisk, List<LifeDescription> carWashing,
                         List<LifeDescription> ultraviolet, List<LifeDescription> dressing) {
            this.coldRisk = coldRisk;
            this.carWashing = carWashing;
            this.ultraviolet = ultraviolet;
            this.dressing = dressing;
        }

        public List<LifeDescription> getColdRisk() {
            return coldRisk;
        }

        public List<LifeDescription> getCarWashing() {
            return carWashing;
        }

        public List<LifeDescription> getUltraviolet() {
            return ultraviolet;
        }

        public List<LifeDescription> getDressing() {
            return dressing;
        }
    }

    public static class LifeDescription {
        private String desc;

        public LifeDescription(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }
}
