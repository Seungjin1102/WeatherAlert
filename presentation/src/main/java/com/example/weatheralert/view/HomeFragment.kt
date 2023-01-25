package com.example.weatheralert.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.weatheralert.R
import com.example.weatheralert.base.BaseFragment
import com.example.weatheralert.databinding.FragmentHomeBinding
import com.example.weatheralert.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, WeatherViewModel>(R.layout.fragment_home) {

    override val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel

    }


}