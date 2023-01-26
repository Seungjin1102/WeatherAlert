package com.example.weatheralert.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener

import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object WeatherUtil {

    var locationManager: LocationManager? = null

    fun getCurrentDay(): Int {
        val current = LocalDateTime.now()
        return current.format(DateTimeFormatter.ISO_DATE).replace("-", "").toInt()
    }


    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatted = current.format(DateTimeFormatter.ISO_LOCAL_TIME).replace(":", "").substring(0, 2) + "00"
        var time = 0
        val timeList = listOf(200, 500, 800, 1100, 1400, 1700, 2000, 2300)

        timeList.forEach {
            if (formatted.toInt() >= it) time = it
        }

        return if (time.toString().length < 4) "0$time"
        else time.toString()
    }




    fun dfsXyConv(v1: Double, v2: Double) : Point {
        val RE = 6371.00877     // 지구 반경(km)
        val GRID = 5.0          // 격자 간격(km)
        val SLAT1 = 30.0        // 투영 위도1(degree)
        val SLAT2 = 60.0        // 투영 위도2(degree)
        val OLON = 126.0        // 기준점 경도(degree)
        val OLAT = 38.0         // 기준점 위도(degree)
        val XO = 43             // 기준점 X좌표(GRID)
        val YO = 136            // 기준점 Y좌표(GRID)
        val DEGRAD = Math.PI / 180.0
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn
        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / Math.pow(ro, sn)

        var ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5)
        ra = re * sf / Math.pow(ra, sn)
        var theta = v2 * DEGRAD - olon
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta *= sn

        val x = (ra * Math.sin(theta) + XO + 0.5).toInt()
        val y = (ro - ra * Math.cos(theta) + YO + 0.5).toInt()
        Timber.d("x: $x y: $y")
        return Point(x, y)
    }

//    fun getLocation1(activity: Activity) {
//        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//        var latitude = 0.0
//        var longitude = 0.0
//        var userLocation: Location = getLatLng(activity)
//        if(userLocation != null){
//            latitude = userLocation.latitude
//            longitude = userLocation.longitude
//            Timber.d("latitude: $latitude longitude: $longitude")
//
//            var mGeoCoder =  Geocoder(activity.applicationContext, Locale.KOREAN)
////        var mResultList: List<Address>? = null
////        try{
////            mResultList = mGeoCoder.getFromLocation(
////                latitude!!, longitude!!, 1
////            )
////        }catch(e: IOException){
////            e.printStackTrace()
////        }
////        if(mResultList != null){
////            Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
////        }
//        }
//    }

    @SuppressLint("MissingPermission")
    private fun getLatLng(activity: Activity): Location{
        var currentLatLng: Location? = null
    var hasFineLocationPermission = ContextCompat.checkSelfPermission(activity,
        Manifest.permission.ACCESS_FINE_LOCATION)
    var hasCoarseLocationPermission = ContextCompat.checkSelfPermission(activity,
        Manifest.permission.ACCESS_COARSE_LOCATION)

        if(true){
            val locatioNProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locatioNProvider)
        }else{
//            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, REQUIRED_PERMISSIONS[0])){
//                Toast.makeText(this, "앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
//                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
//            }else{
//                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
//            }
//            currentLatLng = getLatLng()
        }
        return currentLatLng!!
    }

    fun getLocation(activity: Activity): Point? {
        var point: Point? = null
        runBlocking {
            requestPermission {
                locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                var latitude = 0.0
                var longitude = 0.0
                var userLocation: Location = getLatLng(activity)

                if (userLocation != null) {
                    latitude = userLocation.latitude
                    longitude = userLocation.longitude
                    Timber.d("latitude: $latitude longitude: $longitude")
                    point = dfsXyConv(latitude, longitude)
                    return@requestPermission
                }
            }
        }
        return point
    }

    private fun requestPermission(logic: () -> Unit) {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    logic()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Timber.d("위치 권한 없음")
                }
            })
            .setDeniedMessage("권한을 허용해주세요")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }


}