package com.example.weatheralert.util

import android.content.Context
import android.util.TypedValue
import com.example.weatheralert.di.WeatherApplication

object ResourceUtil {

    fun getString(resId: Int): String {
        return WeatherApplication.applicationContext().getString(resId)
    }

    fun getColor(resId: Int): Int {
        return WeatherApplication.applicationContext().getColor(resId)
    }

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            WeatherApplication.applicationContext().resources.displayMetrics
        ).toInt()
    }

}