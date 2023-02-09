package com.example.data

import android.util.Log
import com.example.data.mapper.mapperToWeather
import com.example.data.repository.weather.remote.WeatherRemoteDataSource
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.WeatherEntity
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val weatherRemoteDataSource: WeatherRemoteDataSource): WeatherRepository {

    override suspend fun getWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<List<WeatherEntity>> {
        Log.d("WeatherViewModel", "WeatherRepositoryImpl getWeather()")
        return flow {
            weatherRemoteDataSource.getWeather(numOfRows, pageNo, dataType, base_date, base_time, nx, ny).collect {
                Log.d("WeatherViewModel", "it: $it")
                emit(mapperToWeather(items = it.response.body.items))
            }
        }
    }

    override suspend fun getMidTmpWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ): Flow<List<MidWeatherEntity.MidTmpWeatherEntity>> {
        TODO("Not yet implemented")
    }
}