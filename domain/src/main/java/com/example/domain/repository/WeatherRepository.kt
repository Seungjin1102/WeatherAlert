package com.example.domain.repository

import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getShortWeather(
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<List<ShortWeatherEntity>>

    suspend fun getMidTmpWeather(
        regId: String,
        tmFc: String
    ) : Flow<List<MidWeatherEntity.MidTmpWeatherEntity>>

    suspend fun getMidSkyWeather(
        regId: String,
        tmFc: String
    ) : Flow<List<MidWeatherEntity.MidSkyWeatherEntity>>

}