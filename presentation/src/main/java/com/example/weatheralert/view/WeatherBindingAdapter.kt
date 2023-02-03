package com.example.weatheralert.view

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.domain.model.WeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.viewmodel.WeatherViewModel

object WeatherBindingAdapter {

    @JvmStatic
    @BindingAdapter("isLoading")
    fun isLoading(view: ProgressBar, uiState: WeatherViewModel.UiState) {
        view.visibility = if (uiState is WeatherViewModel.UiState.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("isShow")
    fun isShow(view: View, uiState: WeatherViewModel.UiState) {
        view.visibility = if (uiState is WeatherViewModel.UiState.Loading) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("currentWeather")
    fun View.bindCurrentWeather(uiState: WeatherViewModel.UiState) {
        if (uiState is WeatherViewModel.UiState.Success<*>) {
            if (uiState.data is WeatherEntity) {
                this.findViewById<TextView>(R.id.temperatures).text = uiState.data.tmp
            }
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