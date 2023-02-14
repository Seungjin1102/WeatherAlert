package com.example.domain.model

data class MidWeatherEntity(
    val midTmp: MidTmpWeatherEntity,
    val midSky: MidSkyWeatherEntity,
) {
    data class MidTmpWeatherEntity(
        val minTmp: Int,
        val maxTmp: Int
    )

    data class MidSkyWeatherEntity(
        val amPop: Int,
        val pmPop: Int,
        val amSky: String,
        val pmSky: String
    )
}


