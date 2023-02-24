package com.example.weatheralert.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.weatheralert.R
import com.example.weatheralert.base.BaseFragment
import com.example.weatheralert.databinding.FragmentHomeBinding
import com.example.weatheralert.util.ResourceUtil
import com.example.weatheralert.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, WeatherViewModel>(R.layout.fragment_home) {

    override val viewModel: WeatherViewModel by viewModels()
    private var isCheckPermission: Boolean = false

    private lateinit var locationManager: LocationManager
    private lateinit var weatherLocationListener: WeatherLocationListener

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

        startLocationTrack()
    }

    private fun startLocationTrack() {
        if (::locationManager.isInitialized.not()) {
            locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        }

        Timber.d("network state: ${checkNetworkState()}\tgps state: ${locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)}")

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            viewModel.failGpsState(ResourceUtil.getString(R.string.weather_error_gps_state))
        } else if (!checkNetworkState()) {
            viewModel.failNetWorkState(ResourceUtil.getString(R.string.weather_error_network_state))
        } else {
            setLocationListener()
        }

    }

    @SuppressLint("MissingPermission")
    private fun setLocationListener() {
        val minTime: Long = 1000
        val minDistance = 100f

        if (::weatherLocationListener.isInitialized.not()) {
            weatherLocationListener = WeatherLocationListener()
        }

        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                weatherLocationListener
            )

            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                weatherLocationListener
            )
        }
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

    private fun checkNetworkState(): Boolean {
        val connectivityManager: ConnectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        val network: Network = connectivityManager.activeNetwork ?: return false
        val actNetwork: NetworkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }

    inner class WeatherLocationListener : LocationListener {
        override fun onLocationChanged(p0: Location) {
            Timber.d("location: $p0")
            viewModel.getAddress(requireActivity(), p0)
            removeLocationListener()
        }

        private fun removeLocationListener() {
            if (::locationManager.isInitialized && ::weatherLocationListener.isInitialized) {
                locationManager.removeUpdates(weatherLocationListener)
            }
        }
    }

}