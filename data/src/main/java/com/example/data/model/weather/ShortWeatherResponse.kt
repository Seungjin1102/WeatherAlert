package com.example.data.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShortWeatherResponse(
    @field:Json(name = "baseDate") val baseDate: String,
    @field:Json(name = "baseTime") val baseTime: String,
    @field:Json(name = "category") val category: String,
    @field:Json(name = "fcstDate") val fcstDate: String,
    @field:Json(name = "fcstTime") val fcstTime: String,
    @field:Json(name = "fcstValue") val fcstValue: String,
    @field:Json(name = "nx") val nx: Int,
    @field:Json(name = "ny") val ny: Int,
)
