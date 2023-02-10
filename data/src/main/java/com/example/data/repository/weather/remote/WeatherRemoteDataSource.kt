package com.example.data.repository.weather.remote

import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {

    suspend fun getWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<WeatherResponse>

    suspend fun getMidTmpWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ) : Flow<MidTmpWeatherResponse>

    suspend fun getMidSkyWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ) : Flow<MidSkyWeatherResponse>

}