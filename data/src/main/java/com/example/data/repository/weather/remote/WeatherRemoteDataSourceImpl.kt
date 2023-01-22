package com.example.data.repository.weather.remote

import com.example.domain.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

class WeatherRemoteDataSourceImpl: WeatherRemoteDataSource {
    override suspend fun getShortWeather(): Flow<WeatherEntity> {
        TODO("Not yet implemented")
    }
}