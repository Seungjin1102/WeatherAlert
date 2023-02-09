package com.example.domain.model

data class MidWeatherEntity(
    val midTmp: MidTmpWeatherEntity,
    val midSky: MidSkyWeatherEntity
) {
    data class MidTmpWeatherEntity(
        val minTmp: String,
        val maxTmp: String
    )

    data class MidSkyWeatherEntity(
        val amPop: String,
        val pmPop: String,
        val amSky: String,
        val pmSky: String
    )
}


