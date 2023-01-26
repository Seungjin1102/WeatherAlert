package com.example.weatheralert.view

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.weatheralert.viewmodel.WeatherViewModel

object WeatherBindingAdapter {

    @JvmStatic
    @BindingAdapter("isLoading")
    fun isLoading(view: ProgressBar, uiState: WeatherViewModel.UiState) {
        view.visibility = if (uiState is WeatherViewModel.UiState.Loading) View.VISIBLE else View.GONE
    }
}