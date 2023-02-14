package com.example.weatheralert.view

import android.Manifest
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
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, WeatherViewModel>(R.layout.fragment_home) {

    override val viewModel: WeatherViewModel by viewModels()
    private var isCheckPermission: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shortWeatherUiState.collect {
//                    handleState(it)
                }
            }
        }

        binding.title.setOnClickListener {
//            getWeather()
        }

        requestPermission()
//        getWeather()
    }

    private fun getWeather() {
        if (!isCheckPermission) {
            Toast.makeText(requireContext(), "위치 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.getAddress(requireActivity())
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    isCheckPermission = true
//                    getWeather()
//                    CoroutineScope(Dispatchers.Main).launch {
//                        viewModel.getAddress(requireActivity())
//                    }
                    getWeather()
//                    viewModel.getAddress(requireActivity())
                    Timber.d("코루틴 밖")
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Timber.d("위치 권한 없음")
                    isCheckPermission = false
                }
            })
            .setDeniedMessage("권한을 허용해주세요.")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }

//    private fun handleState(uiState: UiState) = when (uiState) {
//        is UiState.Loading -> Toast.makeText(requireContext(), "로딩 중", Toast.LENGTH_SHORT).show()
//        is UiState.Success<*> -> {
//           if (uiState.data is List<*>) {
////               binding.textView.text = uiState.data.toString()
//           } else {
//               binding.address.text = uiState.data.toString()
//           }
//        }
//        is UiState.Error -> Toast.makeText(requireContext(), "에러 발생", Toast.LENGTH_SHORT).show()
//    }
}