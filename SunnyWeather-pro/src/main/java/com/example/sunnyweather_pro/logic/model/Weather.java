package com.example.sunnyweather_pro.logic.model;

/*天气封装*/
public class Weather {

    public RealtimeResponse.Realtime realtime;
    public DailyResponse.Daily daily;

    public Weather(RealtimeResponse.Realtime realtime, DailyResponse.Daily daily) {
        this.realtime = realtime;
        this.daily = daily;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "realtime=" + realtime +
                ", daily=" + daily +
                '}';
    }
}
