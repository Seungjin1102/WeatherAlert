package com.example.weatheralert.view

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.domain.model.WeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.base.UiState
import timber.log.Timber

object WeatherBindingAdapter {

    @JvmStatic
    @BindingAdapter("isLoading")
    fun ProgressBar.isLoading(uiState: UiState<*>) {
        this.visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("isShow")
    fun View.isShow(uiState: UiState<List<WeatherEntity>>) {
        this.visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("currentWeather")
    fun View.bindCurrentWeather(uiState: UiState<List<WeatherEntity>>) {
        if (uiState is UiState.Success<*>) {
            if (uiState.data is WeatherEntity) {
                this.findViewById<TextView>(R.id.temperatures).text = uiState.data.tmp
            }
        }
    }

    @JvmStatic
    @BindingAdapter("todayItem")
    fun RecyclerView.bindTodayItem(uiState: UiState<List<WeatherEntity>>) {
        if (uiState is UiState.Success) {
            val adapter = TodayWeatherAdapter()
            this.adapter = adapter
            val currentDay = uiState.data.first().date
            adapter.submitList(uiState.data.filter { it.date.toInt() < currentDay.toInt() + 3 })
            this.addItemDecoration(TodayWeatherAdapter.TodayWeatherItemDecoration())
        }
    }

    @JvmStatic
    @BindingAdapter("weeklyItem")
    fun RecyclerView.bindWeeklyItem(uiState: UiState<List<WeatherEntity>>) {
        if (uiState is UiState.Success) {
            val adapter = WeeklyWeatherAdapter()
            this.adapter = adapter
            var lastDay = ""
            val weeklyList = mutableListOf<WeatherEntity>()
            uiState.data.forEach {
                if (lastDay != it.date) {
                    lastDay = it.date
                    weeklyList.add(it)
                }
            }
            adapter.submitList(weeklyList)
        }
    }

    @JvmStatic
    @BindingAdapter("sky", "pty")
    fun ImageView.bindCurrentSky(sky: String?, pty: String?) {
        if (sky != null && pty != null) {
            this.setImageResource(when(sky) {
                "1" -> R.drawable.sun
                "3" -> R.drawable.sun_cloudy
                else -> {
                    when(pty) {
                        "1", "4" -> R.drawable.rain
                        "2" -> R.drawable.snow
                        "3" -> R.drawable.rain_snow
                        else -> R.drawable.cloud
                    }
                }
            })
        }
    }



}