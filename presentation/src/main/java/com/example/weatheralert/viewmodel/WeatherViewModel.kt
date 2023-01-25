package com.example.weatheralert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WeatherEntity
import com.example.domain.usecase.GetWeatherUseCase
import com.example.weatheralert.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _weatherFlow = MutableStateFlow<List<WeatherEntity>?>(null)
    val weatherFlow = _weatherFlow.asStateFlow()

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
                Timber.d("getWeatherUseCase.execute 시작")
            }.catch {
                Timber.d("getWeatherUseCase.execute catch $this")
            }.collect {
                Timber.d("collect it: $it")
                _weatherFlow.value = it
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun errorWeather() {
        event(Event.Error("", ""))
    }



    sealed class Event {

        object Loading: Event()

//        data class Success(): Event()

        data class Error(val code: String, val message: String): Event()
    }



}