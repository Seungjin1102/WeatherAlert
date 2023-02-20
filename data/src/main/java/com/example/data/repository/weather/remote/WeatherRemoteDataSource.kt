package com.example.data.repository.weather.remote

import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.ShortWeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {

    suspend fun getShortWeather(
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<List<ShortWeatherResponse>>

    suspend fun getMidTmpWeather(
        regId: String,
        tmFc: String
    ) : Flow<List<MidTmpWeatherResponse>>

    suspend fun getMidSkyWeather(
        regId: String,
        tmFc: String
    ) : Flow<List<MidSkyWeatherResponse>>

}