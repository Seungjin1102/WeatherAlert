package com.example.data

import com.example.domain.model.WeatherEntity
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl: WeatherRepository {

    override suspend fun getWeather(): Flow<WeatherEntity> {
        TODO()
    }
}