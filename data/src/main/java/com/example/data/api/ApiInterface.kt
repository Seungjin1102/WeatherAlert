package com.example.data.api

import com.example.data.BuildConfig
import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.ShortWeatherResponse
import com.example.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiInterface {

    @GET("VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${BuildConfig.SHORT_WEATHER_API_KEY}")
    suspend fun getShortWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("base_date") base_date: Int,
        @Query("base_time") base_time: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ): Response<WeatherResponse<ShortWeatherResponse>>

    @GET("MidFcstInfoService/getMidTa?serviceKey=${BuildConfig.MID_WEATHER_API_KEY}")
    suspend fun getMidTmpWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String
    ) : Response<WeatherResponse<MidTmpWeatherResponse>>

    @GET("MidFcstInfoService/getMidLandFcst?serviceKey=${BuildConfig.MID_WEATHER_API_KEY}")
    suspend fun getMidSkyWeather(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String,
        @Query("regId") regId: String,
        @Query("tmFc") tmFc: String
    ) : Response<WeatherResponse<MidSkyWeatherResponse>>

}