package com.example.weatheralert.view

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.WeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.base.UiState
import com.example.weatheralert.base.successOrNull
import com.example.weatheralert.viewmodel.WeatherViewModel
import timber.log.Timber

object WeatherBindingAdapter {

    @JvmStatic
    @BindingAdapter("isLoading")
    fun isLoading(view: ProgressBar, uiState: UiState<*>) {
        view.visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("isShow")
    fun isShow(view: View, uiState: UiState<List<WeatherEntity>>) {
        view.visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
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
        val boundAdapter = this.adapter
        Timber.d("boundAdapter: $boundAdapter")
        if (boundAdapter is TodayWeatherAdapter) {
            boundAdapter.submitList(uiState.successOrNull())
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