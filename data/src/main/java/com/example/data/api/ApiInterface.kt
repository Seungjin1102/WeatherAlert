package com.example.data.api

import com.example.data.BuildConfig
import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${BuildConfig.WEATHER_API_KEY}")
    suspend fun getWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("base_date") base_date: Int,
        @Query("base_time") base_time: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ): WeatherResponse

    @GET("MidFcstInfoService/getMidTa?serviceKey=${BuildConfig.MID_WEATHER_API_KEY}")
    suspend fun getMidTmpWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String
    ) : MidTmpWeatherResponse

    @GET("MidFcstInfoService/getMidLandFcst?serviceKey=${BuildConfig.MID_WEATHER_API_KEY}")
    suspend fun getMidSkyWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String
    ) : MidSkyWeatherResponse

}