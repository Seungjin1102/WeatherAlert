package com.example.data.api

import com.example.data.BuildConfig
import com.example.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MidWeatherInterface {

    @GET("getMidTa?serviceKey=${BuildConfig.MID_WEATHER_API_KEY}")
    suspend fun getMidTmpWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String,
    ): WeatherResponse
}