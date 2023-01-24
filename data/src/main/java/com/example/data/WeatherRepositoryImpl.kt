package com.example.data

import com.example.data.repository.weather.remote.WeatherRemoteDataSource
import com.example.domain.model.WeatherEntity
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(private val weatherRemoteDataSource: WeatherRemoteDataSource): WeatherRepository {

    override suspend fun getWeather(): Flow<List<WeatherEntity>> {
        TODO()
    }
}