package com.example.domain.repository

import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getShortWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<List<ShortWeatherEntity>>

    suspend fun getMidTmpWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ) : Flow<List<MidWeatherEntity.MidTmpWeatherEntity>>

    suspend fun getMidSkyWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ) : Flow<List<MidWeatherEntity.MidSkyWeatherEntity>>

}