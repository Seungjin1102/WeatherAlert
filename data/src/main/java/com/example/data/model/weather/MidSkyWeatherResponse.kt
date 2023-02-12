package com.example.data.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MidSkyWeatherResponse(
    @field:Json(name = "regId") val regId: String,

    @field:Json(name = "rnSt3Am") val rnSt3Am: Int,
    @field:Json(name = "rnSt3Pm") val rnSt3Pm: Int,
    @field:Json(name = "rnSt4Am") val rnSt4Am: Int,
    @field:Json(name = "rnSt4Pm") val rnSt4Pm: Int,
    @field:Json(name = "rnSt5Am") val rnSt5Am: Int,
    @field:Json(name = "rnSt5Pm") val rnSt5Pm: Int,
    @field:Json(name = "rnSt6Am") val rnSt6Am: Int,
    @field:Json(name = "rnSt6Pm") val rnSt6Pm: Int,
    @field:Json(name = "rnSt7Am") val rnSt7Am: Int,
    @field:Json(name = "rnSt7Pm") val rnSt7Pm: Int,

    @field:Json(name = "wf3Am") val wf3Am: String,
    @field:Json(name = "wf3Pm") val wf3Pm: String,
    @field:Json(name = "wf4Am") val wf4Am: String,
    @field:Json(name = "wf4Pm") val wf4Pm: String,
    @field:Json(name = "wf5Am") val wf5Am: String,
    @field:Json(name = "wf5Pm") val wf5Pm: String,
    @field:Json(name = "wf6Am") val wf6Am: String,
    @field:Json(name = "wf6Pm") val wf6Pm: String,
    @field:Json(name = "wf7Am") val wf7Am: String,
    @field:Json(name = "wf7Pm") val wf7Pm: String,
)

