package com.example.weatheralert.view

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.weatheralert.R
import com.example.weatheralert.base.BaseFragment
import com.example.weatheralert.databinding.FragmentHomeBinding
import com.example.weatheralert.util.ResourceUtil
import com.example.weatheralert.util.WeatherUtil
import com.example.weatheralert.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, WeatherViewModel>(R.layout.fragment_home) {

    override val viewModel: WeatherViewModel by viewModels()
    private var isCheckPermission: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.fragment = this
        requestPermission()
    }

    fun getWeather() {
        if (!isCheckPermission) {
            Toast.makeText(requireContext(), ResourceUtil.getString(R.string.weather_permission_gps_message), Toast.LENGTH_SHORT).show()
            return
        }

        val location = WeatherUtil.getLocation(requireActivity())
        viewModel.getAddress(requireActivity(), location)
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    isCheckPermission = true
                    getWeather()
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Timber.d("위치 권한 없음")
                    isCheckPermission = false
                }
            })
            .setDeniedMessage(ResourceUtil.getString(R.string.weather_permission_gps_message))
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }
}