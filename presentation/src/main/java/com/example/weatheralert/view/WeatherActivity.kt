package com.example.weatheralert.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatheralert.R
import com.example.weatheralert.base.BaseActivity
import com.example.weatheralert.databinding.ActivityWeatherBinding
import com.example.weatheralert.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : BaseActivity<ActivityWeatherBinding, WeatherViewModel>(R.layout.activity_weather) {

    override val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}