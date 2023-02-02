package com.example.weatheralert.view

import android.view.View
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
    @BindingAdapter("currentWeather")
    fun View.bindCurrentWeather(uiState: WeatherViewModel.UiState) {
        if (uiState is WeatherViewModel.UiState.Success<*>) {
            if (uiState.data is WeatherEntity) {
                this.findViewById<TextView>(R.id.temperatures).text = uiState.data.tmp
            }

        }

    }

}