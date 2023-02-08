package com.example.weatheralert.di

import android.app.Application
import android.content.Context
import com.example.weatheralert.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WeatherApplication: Application() {

    init{
        instance = this
    }

    companion object {
        lateinit var instance: WeatherApplication
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }

}

class TimberDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.fileName}:${element.lineNumber}#${element.methodName}"
    }

}