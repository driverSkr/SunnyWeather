package com.example.SunnyWeather.logic.model;

import androidx.annotation.DrawableRes;

public class Sky {
    private String info;
    @DrawableRes
    private int icon;
    @DrawableRes
    private int bg;

    public Sky(String info, int icon, int bg) {
        this.info = info;
        this.icon = icon;
        this.bg = bg;
    }

    public String getInfo() {
        return info;
    }

    public int getIcon() {
        return icon;
    }

    public int getBg() {
        return bg;
    }
}
