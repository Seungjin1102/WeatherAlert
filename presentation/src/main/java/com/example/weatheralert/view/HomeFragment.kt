package com.example.weatheralert.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weatheralert.R
import com.example.weatheralert.base.BaseFragment
import com.example.weatheralert.databinding.FragmentHomeBinding
import com.example.weatheralert.util.WeatherUtil
import com.example.weatheralert.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.weatheralert.viewmodel.WeatherViewModel.UiState
import timber.log.Timber
import kotlin.math.PI

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, WeatherViewModel>(R.layout.fragment_home) {

    override val viewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Loading -> {}
                        is UiState.Success -> binding.textView.text = it.weatherList.toString()
                        is UiState.Error -> binding.textView.text = "실패"
                    }
                }
            }
        }

        binding.title.setOnClickListener {
            val point = WeatherUtil.getLocation(requireActivity())
            Timber.d("point 다음")
            if (point == null) Toast.makeText(requireContext(), "위치 정보를 얻을 수 없습니다.", Toast.LENGTH_SHORT).show()
            else {
                viewModel.getWeather(
                    737,
                    1,
                    "JSON",
                    WeatherUtil.getCurrentDay(),
                    WeatherUtil.getCurrentTime(),
                    point.x.toString(),
                    point.y.toString()
                )
            }
        }
    }



    private fun handleState(uiState: UiState) = when (uiState) {
        is UiState.Loading -> Toast.makeText(requireContext(), "로딩 중", Toast.LENGTH_SHORT).show()
        is UiState.Success -> Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show()
        is UiState.Error -> Toast.makeText(requireContext(), "에러 발생", Toast.LENGTH_SHORT).show()
    }
}