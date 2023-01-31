package com.example.weatheralert.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetWeatherUseCase
import com.example.weatheralert.base.BaseViewModel
import com.example.weatheralert.util.WeatherUtil
import com.example.weatheralert.view.WeatherActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ) {
        viewModelScope.launch {
            getWeatherUseCase.execute(numOfRows, pageNo, dataType, base_date, base_time, nx, ny).onStart {
                Timber.d("getWeatherUseCase.execute start")
                _uiState.value = UiState.Loading
            }.catch {
                Timber.d("getWeatherUseCase.execute catch")
                _uiState.value = UiState.Error()
            }.collect {
                Timber.d("collect it: $it")
                _uiState.value = UiState.Success(it)

            }
        }
    }

    fun getAddress(context: Context) {
        viewModelScope.launch {
            WeatherUtil.susGetAddress(context as WeatherActivity).collect {
                Timber.d("WeatherViewModel getAddress() collect 안 it: $it")
                _uiState.value = UiState.Success(it)
            }
        }
    }

    sealed class UiState {
        object Loading: UiState()
        data class Success<T>(val data: T): UiState()
        data class Error(val errorCode: String = ""): UiState()
    }
}