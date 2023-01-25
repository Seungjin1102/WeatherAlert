package com.example.data.repository.weather.remote

import android.util.Log
import com.example.data.api.ApiInterface
import com.example.data.model.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRemoteDataSourceImpl(private val apiInterface: ApiInterface): WeatherRemoteDataSource {
    override suspend fun getWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<WeatherResponse> {
        Log.d("WeatherViewModel", "WeatherRemoteDataSourceImpl getWeather()")
        return flow {
            Log.d("WeatherViewModel", "WeatherRemoteDataSourceImpl getWeather() flow 안")
            emit(apiInterface.getWeatherFlow(numOfRows, pageNo, dataType, base_date, base_time, nx, ny))
        }
//        apiInterface.getWeatherFlow(numOfRows, pageNo, dataType, base_date, base_time, nx, ny)
    }
}