package com.example.weatheralert.util

import android.content.Context
import android.content.SharedPreferences
import com.example.weatheralert.di.WeatherApplication

object PreferenceUtil {

    private const val PREFERENCE_KEY = "location"
    const val PREFERENCE_LAST_LATITUDE = "last_latitude"
    const val PREFERENCE_LAST_LONGITUDE = "last_longitude"

    private val prefs: SharedPreferences =
        WeatherApplication.applicationContext().getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)

    fun getLntLng(key: String, default: Float): Float {
        return prefs.getFloat(key, default)
    }

    fun setLntLng(key: String, lntLng: Float) {
        prefs.edit().putFloat(key, lntLng).apply()
    }

}