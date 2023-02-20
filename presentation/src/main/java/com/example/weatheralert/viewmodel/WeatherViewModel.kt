package com.example.weatheralert.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity
import com.example.domain.usecase.GetMidSkyWeatherUseCase
import com.example.domain.usecase.GetMidTmpWeatherUseCase
import com.example.domain.usecase.GetShortWeatherUseCase
import com.example.weatheralert.R
import com.example.weatheralert.base.BaseViewModel
import com.example.weatheralert.base.UiState
import com.example.weatheralert.util.ResourceUtil
import com.example.weatheralert.util.WeatherUtil
import com.example.weatheralert.view.WeatherActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.min


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getShortWeatherUseCase: GetShortWeatherUseCase,
    private val getMidTmpWeatherUseCase: GetMidTmpWeatherUseCase,
    private val getMidSkyWeatherUseCase: GetMidSkyWeatherUseCase
) : BaseViewModel() {

    //단기예보 데이터
    private val _shortWeatherUiState: MutableStateFlow<UiState<List<ShortWeatherEntity>>> = MutableStateFlow(UiState.Loading)
    val shortWeatherUiState = _shortWeatherUiState.asStateFlow()

    //중기예보 데이터
    private val _midWeatherUiState: MutableStateFlow<UiState<List<MidWeatherEntity>>> = MutableStateFlow(UiState.Loading)
    val midWeatherUiState = _midWeatherUiState.asStateFlow()

    //오늘 날씨 데이터
    private val _currentWeather: MutableStateFlow<ShortWeatherEntity?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    //현재 위치 주소 데이터
    private val _addressState: MutableStateFlow<String> = MutableStateFlow("")
    val addressState = _addressState.asStateFlow()

    //단기예보 조회
    private fun getShortWeather(
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ) {
        viewModelScope.launch {
            getShortWeatherUseCase.execute(base_date, base_time, nx, ny).onStart {
                Timber.d("단기예보 flow start")
                _shortWeatherUiState.value = UiState.Loading
            }.catch {
                Timber.d("단기예보 flow catch it: $it")
                _shortWeatherUiState.value = UiState.Error(it)
            }.collect {
                Timber.d("단기예보 collect it: $it")
                _shortWeatherUiState.value = UiState.Success(it)
                _currentWeather.value = it.first()
            }
        }
    }

    //중기예보 조회
    private fun getMidWeather(
        tmpRegId: String,
        skyRegId: String,
        tmFc: String
    ) {
        viewModelScope.launch {
            val midUiStateFlow = getMidTmpWeatherUseCase.execute(tmpRegId, tmFc)
                .zip(getMidSkyWeatherUseCase.execute(skyRegId, tmFc)) { tmpFlow, skyFlow ->
                    val resultList = mutableListOf<MidWeatherEntity>()
                    val today = LocalDateTime.now()
                    for (i in 0 until min(tmpFlow.size, skyFlow.size)) {
                        resultList.add(MidWeatherEntity(tmpFlow[i], skyFlow[i],
                            today.plusDays((3 + i).toLong()).dayOfWeek.name))
                    }
                    resultList
                }

            midUiStateFlow.onStart {
                Timber.d("중기예보 flow start")
                _midWeatherUiState.value = UiState.Loading
            }.catch {
                Timber.d("중기예보 flow catch it: $it")
                _midWeatherUiState.value = UiState.Error(it)
            }.collect {
                Timber.d("중기에보 데이터 collect: $it")
                _midWeatherUiState.value = UiState.Success(it)
            }
        }
    }

    fun getAddress(context: Context, location: Location?) {
        if (location == null) {
            _shortWeatherUiState.value = UiState.Error(Throwable(ResourceUtil.getString(R.string.weather_error_gps), null))
            return
        }
        viewModelScope.launch {
            val point = WeatherUtil.pointConverter(location.latitude, location.longitude)
            getShortWeather(
                WeatherUtil.getShorWeatherDay(),
                WeatherUtil.getShortWeatherTime(),
                point.x.toString(),
                point.y.toString()
            )

            WeatherUtil.getAddress(context as WeatherActivity, location).collect {
                Timber.d("WeatherViewModel getAddress() collect 안 it: $it")
                _addressState.value = it

                val tmpRegId = WeatherUtil.getMidTmpRegId(it)
                val skyRegId = WeatherUtil.getMidSkyRegId(it)

                if (tmpRegId.isNotEmpty() && skyRegId.isNotEmpty()) {
                    getMidWeather(
                        tmpRegId,
                        skyRegId,
                        WeatherUtil.getMidWeatherTime()
                    )
                } else {
                    _midWeatherUiState.value = UiState.Error(Throwable(ResourceUtil.getString(R.string.weather_error_mid_regid), null))
                }
            }
        }
    }

}