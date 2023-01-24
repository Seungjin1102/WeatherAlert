package com.example.weatheralert.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatheralert.R
import com.example.weatheralert.databinding.ActivityWeatherBinding
import com.example.weatheralert.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private var _binding: ActivityWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_weather)
        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}