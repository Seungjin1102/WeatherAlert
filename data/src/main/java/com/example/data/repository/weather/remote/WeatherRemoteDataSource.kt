package com.example.data.repository.weather.remote

import com.example.domain.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {

    suspend fun getShortWeather(): Flow<WeatherEntity> {
        TODO()
    }

}