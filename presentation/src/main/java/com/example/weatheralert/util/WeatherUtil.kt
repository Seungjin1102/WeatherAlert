package com.example.weatheralert.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import com.example.weatheralert.R
import com.example.weatheralert.di.WeatherApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume

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

    /**
     * 현재 GPS return
     */
    fun getLocation(activity: Activity): Point? {
        var point: Point? = null
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var latitude = 0.0
        var longitude = 0.0
        var userLocation: Location = getLatLng(activity)

        if (userLocation != null) {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Timber.d("latitude: $latitude longitude: $longitude")
            point = pointConverter(latitude, longitude)
        }

        return point
    }

    /**
     * 현재 GPS 위경도 return
     */
    @SuppressLint("MissingPermission")
    private fun getLatLng(activity: Activity): Location{
        if (locationManager == null) locationManager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var currentLatLng: Location? = null

        currentLatLng = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)// ?: locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        return currentLatLng!!
    }

    /**
     * 기상청 단기예보에서 쓰는 Point 계산
     */
    private fun pointConverter(v1: Double, v2: Double) : Point {
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

    /**
     * 현재 주소 return
     * 시, 구, 군 까지 표시
     */
    suspend fun susGetAddress(activity: Activity): Flow<String> {
        val geocoder = Geocoder(activity)
        val location = getLatLng(activity)

        val result = suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(location.latitude, location.longitude, 8, object : Geocoder.GeocodeListener {
                    override fun onGeocode(p0: MutableList<Address>) {
                        Timber.d("콜백 안 p0: $p0")
                        continuation.resume(p0)
                    }

                    override fun onError(errorMessage: String?) {
                        Timber.d("콜백 실패")
                        super.onError(errorMessage)
                        continuation.cancel()
                    }
                })
            }  else {
                geocoder.getFromLocation(location.latitude, location.longitude, 8)?.let {
                    continuation.resume(it)
                }
            }
        }

        val address = result[0].let {
            it.adminArea + " " + it.subLocality + " " + it.thoroughfare
        }
        Timber.d("address: $address")

        return flow { emit(address) }
    }

    fun getReadExelTest() {
        val list = WeatherApplication.applicationContext().resources.getStringArray(R.array.mid_regid_tmp)
        val address = "군산"
        var result = ""
        list.forEachIndexed { index, s ->
            if (s.split("|").first() == address) result = s.split("|").last()
        }
        Timber.d("result: $result")

    }

}