package com.example.data.mapper

import com.example.data.model.weather.MidSkyWeatherResponse
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

fun mapperToMidSkyWeather(items: MidSkyWeatherResponse.Items): List<MidWeatherEntity.MidSkyWeatherEntity> {
    val midSkyWeatherList = mutableListOf<MidWeatherEntity.MidSkyWeatherEntity>()

    items.item.first().run {
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt3Am, this.rnSt3Pm, this.wf3Am, this.wf3Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt4Am, this.rnSt4Pm, this.wf4Am, this.wf4Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt5Am, this.rnSt5Pm, this.wf5Am, this.wf5Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt6Am, this.rnSt6Pm, this.wf6Am, this.wf6Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt7Am, this.rnSt7Pm, this.wf7Am, this.wf7Pm))
    }

    return midSkyWeatherList.toList()
}