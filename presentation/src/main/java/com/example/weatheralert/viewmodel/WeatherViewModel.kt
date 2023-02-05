package com.example.weatheralert.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WeatherEntity
import com.example.domain.usecase.GetWeatherUseCase
import com.example.weatheralert.base.BaseViewModel
import com.example.weatheralert.base.UiState
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

    private val _uiState: MutableStateFlow<UiState<List<WeatherEntity>>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _addressState: MutableStateFlow<String> = MutableStateFlow("")
    val addressState = _addressState.asStateFlow()

    private val _currentWeather: MutableStateFlow<WeatherEntity?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

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
                _uiState.value = UiState.Error(null)
            }.collect {
                Timber.d("collect it: $it")
                _uiState.value = UiState.Success(it)
                _currentWeather.value = it.first()
            }
        }
    }

    fun getAddress(context: Context) {
        viewModelScope.launch {
            WeatherUtil.susGetAddress(context as WeatherActivity).collect {
                Timber.d("WeatherViewModel getAddress() collect 안 it: $it")
                _addressState.value = it
            }
        }
    }

//    sealed class UiState<out T> {
//        object Loading: UiState<Nothing>()
//        data class Success<T>(val data: T): UiState<T>()
//        data class Error(val errorCode: String = ""): UiState<Nothing>()
//    }
}