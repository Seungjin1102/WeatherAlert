package com.example.weatheralert.util

import com.example.weatheralert.di.WeatherApplication

object ResourceUtil {

    fun getString(resId: Int): String {
        return WeatherApplication.applicationContext().getString(resId)
    }
}