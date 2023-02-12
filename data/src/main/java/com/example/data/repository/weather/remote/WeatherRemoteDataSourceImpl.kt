package com.example.data.repository.weather.remote

import com.example.data.api.ApiInterface
import com.example.data.exception.EmptyBodyException
import com.example.data.exception.NetworkFailureException
import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.ShortWeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRemoteDataSourceImpl(private val apiInterface: ApiInterface): WeatherRemoteDataSource {

    override suspend fun getShortWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ): Flow<List<ShortWeatherResponse>> {
        return flow {
            val response = apiInterface.getShortWeather(numOfRows, pageNo, dataType, base_date, base_time, nx, ny)
            if (response.isSuccessful) {
                val shortWeatherResponseList: List<ShortWeatherResponse> =
                    response.body()?.response?.body?.items?.item ?: throw EmptyBodyException("[${response.code()}] - ${response.raw()}")
                emit(shortWeatherResponseList)
            } else {
                throw NetworkFailureException("[${response.code()}] - ${response.raw()}")
            }
        }
    }

    override suspend fun getMidTmpWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ): Flow<List<MidTmpWeatherResponse>> {
        return flow {
            val response = apiInterface.getMidTmpWeather(numOfRows, pageNo, dataType, regId, tmFc)
            if (response.isSuccessful) {
                val midTmpWeatherResponseList: List<MidTmpWeatherResponse> =
                    response.body()?.response?.body?.items?.item ?: throw EmptyBodyException("[${response.code()}] - ${response.raw()}")
                emit(midTmpWeatherResponseList)
            } else {
                throw NetworkFailureException("[${response.code()}] - ${response.raw()}")
            }
        }
    }

    override suspend fun getMidSkyWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        regId: String,
        tmFc: String
    ): Flow<List<MidSkyWeatherResponse>> {
        return flow {
            val response = apiInterface.getMidSkyWeather(numOfRows, pageNo, dataType, regId, tmFc)
            if (response.isSuccessful) {
                val midSkyWeatherResponseList: List<MidSkyWeatherResponse> =
                    response.body()?.response?.body?.items?.item ?: throw EmptyBodyException("[${response.code()}] - ${response.raw()}")
                emit(midSkyWeatherResponseList)
            } else {
                throw NetworkFailureException("[${response.code()}] - ${response.raw()}")
            }
        }
    }

}