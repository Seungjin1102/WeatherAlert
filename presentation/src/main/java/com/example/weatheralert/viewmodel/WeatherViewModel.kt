package com.example.weatheralert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WeatherEntity
import com.example.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    private val eventFlow = _eventFlow.asSharedFlow()

    private val _weatherFlow = MutableStateFlow<List<WeatherEntity>?>(null)
    private val weatherFlow = _weatherFlow.asStateFlow()

    private fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase.execute().onStart {

            }.catch {

            }.collect {
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