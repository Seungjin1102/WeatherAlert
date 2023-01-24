package com.example.domain.usecase

import com.example.domain.model.WeatherEntity
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend fun execute(): Flow<List<WeatherEntity>> = repository.getWeather()
}