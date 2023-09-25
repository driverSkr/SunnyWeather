package com.example.sunnyweather_pro.logic.model;

import androidx.annotation.DrawableRes;

public class Sky {
    private final String info;
    @DrawableRes
    private final int icon;
    @DrawableRes
    private final int bg;

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
