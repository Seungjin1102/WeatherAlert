package com.example.data.api

import com.example.data.BuildConfig
import com.example.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("getVilageFcst?serviceKey=${BuildConfig.WEATHER_API_KEY}")
    suspend fun getWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("base_date") base_date: Int,
        @Query("base_time") base_time: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ): WeatherResponse
}