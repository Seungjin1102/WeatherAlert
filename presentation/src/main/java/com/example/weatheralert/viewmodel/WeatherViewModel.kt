package com.example.weatheralert.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity
import com.example.domain.usecase.GetMidSkyWeatherUseCase
import com.example.domain.usecase.GetMidTmpWeatherUseCase
import com.example.domain.usecase.GetShortWeatherUseCase
import com.example.weatheralert.base.BaseViewModel
import com.example.weatheralert.base.UiState
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

    private val _shortWeatherUiState: MutableStateFlow<UiState<List<ShortWeatherEntity>>> = MutableStateFlow(UiState.Loading)
    val shortWeatherUiState = _shortWeatherUiState.asStateFlow()

    private val _addressState: MutableStateFlow<String> = MutableStateFlow("")
    val addressState = _addressState.asStateFlow()

    private val _currentWeather: MutableStateFlow<ShortWeatherEntity?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    private val _midWeatherUiState: MutableStateFlow<UiState<List<MidWeatherEntity>>> = MutableStateFlow(UiState.Loading)
    val midWeatherUiState = _midWeatherUiState.asStateFlow()

    //단기예보 조회
    private fun getShortWeather(
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        base_date: Int,
        base_time: String,
        nx: String,
        ny: String
    ) {
        viewModelScope.launch {
            getShortWeatherUseCase.execute(numOfRows, pageNo, dataType, base_date, base_time, nx, ny).onStart {
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
        numOfRows: Int,
        pageNo: Int,
        dataType: String,
        tmpRegId: String,
        skyRegId: String,
        tmFc: String
    ) {
        viewModelScope.launch {
            val midUiStateFlow = getMidTmpWeatherUseCase.execute(numOfRows, pageNo, dataType, tmpRegId, tmFc)
                .zip(getMidSkyWeatherUseCase.execute(numOfRows, pageNo, dataType, skyRegId, tmFc)) { tmpFlow, skyFlow ->
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

    fun getAddress(context: Context) {
        viewModelScope.launch {
            WeatherUtil.getAddress(context as WeatherActivity).collect {
                Timber.d("WeatherViewModel getAddress() collect 안 it: $it")
                _addressState.value = it

                //point를 못가져오면 단기예보 처리 불가 별도의 예외처리 필요
                val point = WeatherUtil.getLocation(context)

                if (point != null) {
                    getShortWeather(
                        737,
                        1,
                        "JSON",
                        WeatherUtil.getShorWeatherDay(),
                        WeatherUtil.getShortWeatherTime(),
                        point.x.toString(),
                        point.y.toString()
                    )
                } else {
                    _shortWeatherUiState.value = UiState.Error(Throwable("단기예보 GPS 관련 에러 발생", null))
                }

                val tmpRegId = WeatherUtil.getMidTmpRegId(it)
                val skyRegId = WeatherUtil.getMidSkyRegId(it)

                if (tmpRegId.isNotEmpty() && skyRegId.isNotEmpty()) {
                    getMidWeather(
                        30,
                        1,
                        "JSON",
                        tmpRegId,
                        skyRegId,
                        WeatherUtil.getMidWeatherTime()
                    )
                } else {
                    _midWeatherUiState.value = UiState.Error(Throwable("중기예보 지역코드 관련 에러 발생", null))
                }
            }
        }
    }

}