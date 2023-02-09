package com.example.data.mapper

import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.WeatherResponse
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.WeatherEntity

fun mapperToWeather(items: WeatherResponse.Items): List<WeatherEntity> {
    val weatherEntityList = mutableListOf<WeatherEntity>()
    val itemsList = items.item.toMutableList()

    while (itemsList.size >= 12) {
        val item = itemsList.subList(0, 12)
        val weatherEntity = WeatherEntity(item[0].fcstDate, item[0].fcstTime, "", "", "", "")

        item.filter { it.category == "TMP" }.map { weatherEntity.tmp = it.fcstValue }
        item.filter { it.category == "POP" }.map { weatherEntity.pop = it.fcstValue }
        item.filter { it.category == "SKY" }.map { weatherEntity.sky = it.fcstValue }
        item.filter { it.category == "PTY" }.map { weatherEntity.pty = it.fcstValue }

        weatherEntityList.add(weatherEntity)

        itemsList.removeAll(item)
    }
    return weatherEntityList.toList()
}

fun mapperToMidTmpWeather(items: MidTmpWeatherResponse.Items): List<MidWeatherEntity.MidTmpWeatherEntity> {
    val midTmpWeatherList = mutableListOf<MidWeatherEntity.MidTmpWeatherEntity>()

    items.item.first().run {
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin3, this.taMax3))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin4, this.taMax4))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin5, this.taMax5))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin6, this.taMax6))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin7, this.taMax7))
    }

    return midTmpWeatherList.toList()
}