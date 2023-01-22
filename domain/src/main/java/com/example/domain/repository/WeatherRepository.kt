package com.example.domain.repository

import com.example.domain.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(): Flow<WeatherEntity>
}