package com.example.SunnyWeather.logic.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

//未来几天的天气数据
public class DailyResponse {
    //响应状态
    private String status;
    //响应结果
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
        //温度信息
        private List<Temperature> temperature;
        //天气类型
        private List<Skycon> skycon;
        //生活指数
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
    //温度信息
    public static class Temperature {
        //最大温度
        private float max;
        //最小温度
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

    //天气类型
    public static class Skycon {
        //天气类型
        private String value;
        //时间
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

    //生活指数
    public static class LifeIndex {
        //感冒
        private List<LifeDescription> coldRisk;
        //洗车
        private List<LifeDescription> carWashing;
        //紫外线
        private List<LifeDescription> ultraviolet;
        //穿衣
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
    //生活指数的描述
    public static class LifeDescription {
        //描述内容
        private String desc;

        public LifeDescription(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }
}
