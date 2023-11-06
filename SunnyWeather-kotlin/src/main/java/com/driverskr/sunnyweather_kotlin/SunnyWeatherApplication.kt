package com.driverskr.sunnyweather_kotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @Author: driverSkr
 * @Time: 2023/11/6 15:03
 * @Description: 全局Application$
 */
class SunnyWeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "lU0rIYm9Ba1sWHY5"
    }
}