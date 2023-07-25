package com.example.SunnyWeather.logic.model;

public class Weather {
    private RealtimeResponse.Realtime realtime;
    private DailyResponse.Daily daily;

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

    public RealtimeResponse.Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeResponse.Realtime realtime) {
        this.realtime = realtime;
    }

    public DailyResponse.Daily getDaily() {
        return daily;
    }

    public void setDaily(DailyResponse.Daily daily) {
        this.daily = daily;
    }
}
