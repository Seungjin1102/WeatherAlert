package com.example.data

import com.example.data.mapper.mapperToMidSkyWeather
import com.example.data.mapper.mapperToMidTmpWeather
import com.example.data.mapper.mapperToShortWeather
import com.example.data.repository.weather.remote.WeatherRemoteDataSource
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val weatherRemoteDataSource: WeatherRemoteDataSource): WeatherRepository {

    override suspend fun getShortWeather(
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<List<ShortWeatherEntity>> {
        return flow {
            weatherRemoteDataSource.getShortWeather(base_date, base_time, nx, ny).collect {
                emit(mapperToShortWeather(items = it))
            }
        }
    }

    override suspend fun getMidTmpWeather(
        regId: String,
        tmFc: String
    ): Flow<List<MidWeatherEntity.MidTmpWeatherEntity>> {
        return flow {
            weatherRemoteDataSource.getMidTmpWeather(regId, tmFc).collect {
                emit(mapperToMidTmpWeather(it))
            }
        }
    }

    override suspend fun getMidSkyWeather(
        regId: String,
        tmFc: String
    ): Flow<List<MidWeatherEntity.MidSkyWeatherEntity>> {
        return flow {
            weatherRemoteDataSource.getMidSkyWeather(regId, tmFc).collect {
                emit(mapperToMidSkyWeather(it))
            }
        }
    }

}