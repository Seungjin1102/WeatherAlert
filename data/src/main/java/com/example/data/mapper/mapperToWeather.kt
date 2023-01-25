package com.example.data.mapper

import android.util.Log
import com.example.data.model.weather.WeatherResponse
import com.example.domain.model.WeatherEntity

fun mapperToWeather(items: WeatherResponse.Items): List<WeatherEntity> {

    val weatherEntityList = mutableListOf<WeatherEntity>()
    val itemsList = items.item.toMutableList()

    while (itemsList.size >= 12) {
        val item = itemsList.subList(0, 11)
        var weatherEntity = WeatherEntity(item[0].fcstDate, item[0].fcstTime, "", "", "")

        item.filter { it.category == "TMP" }.map { weatherEntity.tmp = it.fcstValue }
        item.filter { it.category == "POP" }.map { weatherEntity.pop = it.fcstValue }
        item.filter { it.category == "SKY" }.map { weatherEntity.sky = it.fcstValue }

        weatherEntityList.add(weatherEntity)

        itemsList.removeAll(item)
    }

    return weatherEntityList.toList()

}