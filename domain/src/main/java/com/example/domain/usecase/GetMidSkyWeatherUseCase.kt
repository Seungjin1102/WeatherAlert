package com.example.domain.usecase

import com.example.domain.model.MidWeatherEntity
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMidSkyWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend fun execute(
        regId: String,
        tmFc: String
    ) : Flow<List<MidWeatherEntity.MidSkyWeatherEntity>> = repository.getMidSkyWeather(regId, tmFc)

}