package com.example.data.repository.weather.remote

import com.example.data.api.ApiInterface
import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
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
        return flow {
            emit(apiInterface.getWeather(numOfRows, pageNo, dataType, base_date, base_time, nx, ny))
        }
    }

    override suspend fun getMidTmpWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ): Flow<MidTmpWeatherResponse> {
        return flow {
            emit(apiInterface.getMidTmpWeather(numOfRows, pageNo, dataType, regId, tmFc))
        }
    }

    override suspend fun getMidSkyWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ): Flow<MidSkyWeatherResponse> {
        return flow {
            emit(apiInterface.getMidSkyWeather(numOfRows, pageNo, dataType, regId, tmFc))
        }
    }

}