package com.example.data.repository.weather.remote

import com.example.data.api.ApiInterface
import com.example.domain.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

class WeatherRemoteDataSourceImpl(private val apiInterface: ApiInterface): WeatherRemoteDataSource {
    override suspend fun getShortWeather(): Flow<WeatherEntity> {
        TODO("Not yet implemented")
    }
}