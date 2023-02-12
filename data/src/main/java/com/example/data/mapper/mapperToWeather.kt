package com.example.data.mapper

import com.example.data.model.weather.MidSkyWeatherResponse
import com.example.data.model.weather.MidTmpWeatherResponse
import com.example.data.model.weather.ShortWeatherResponse
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity

fun mapperToShortWeather(items: List<ShortWeatherResponse>): List<ShortWeatherEntity> {
    val shortWeatherEntityList = mutableListOf<ShortWeatherEntity>()
    val itemsList = items.toMutableList()

    while (itemsList.size >= 12) {
        val item = itemsList.subList(0, 12)
        val shortWeatherEntity = ShortWeatherEntity(item[0].fcstDate, item[0].fcstTime, "", "", "", "")

        item.filter { it.category == "TMP" }.map { shortWeatherEntity.tmp = it.fcstValue }
        item.filter { it.category == "POP" }.map { shortWeatherEntity.pop = it.fcstValue }
        item.filter { it.category == "SKY" }.map { shortWeatherEntity.sky = it.fcstValue }
        item.filter { it.category == "PTY" }.map { shortWeatherEntity.pty = it.fcstValue }

        shortWeatherEntityList.add(shortWeatherEntity)

        itemsList.removeAll(item)
    }
    return shortWeatherEntityList.toList()
}

fun mapperToMidTmpWeather(items: List<MidTmpWeatherResponse>): List<MidWeatherEntity.MidTmpWeatherEntity> {
    val midTmpWeatherList = mutableListOf<MidWeatherEntity.MidTmpWeatherEntity>()

    items.first().run {
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin3, this.taMax3))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin4, this.taMax4))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin5, this.taMax5))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin6, this.taMax6))
        midTmpWeatherList.add(MidWeatherEntity.MidTmpWeatherEntity(this.taMin7, this.taMax7))
    }

    return midTmpWeatherList.toList()
}

fun mapperToMidSkyWeather(items: List<MidSkyWeatherResponse>): List<MidWeatherEntity.MidSkyWeatherEntity> {
    val midSkyWeatherList = mutableListOf<MidWeatherEntity.MidSkyWeatherEntity>()

    items.first().run {
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt3Am, this.rnSt3Pm, this.wf3Am, this.wf3Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt4Am, this.rnSt4Pm, this.wf4Am, this.wf4Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt5Am, this.rnSt5Pm, this.wf5Am, this.wf5Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt6Am, this.rnSt6Pm, this.wf6Am, this.wf6Pm))
        midSkyWeatherList.add(MidWeatherEntity.MidSkyWeatherEntity(this.rnSt7Am, this.rnSt7Pm, this.wf7Am, this.wf7Pm))
    }

    return midSkyWeatherList.toList()
}