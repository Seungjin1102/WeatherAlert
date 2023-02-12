package com.example.data.model.weather

import com.squareup.moshi.Json

data class MidTmpWeatherResponse(
    @field:Json(name = "regId") val regId: String,

    @field:Json(name = "taMin3") val taMin3: Int,
    @field:Json(name = "taMin3Low") val taMin3Low: Int,
    @field:Json(name = "taMin3High") val taMin3High: Int,
    @field:Json(name = "taMax3") val taMax3: Int,
    @field:Json(name = "taMax3Low") val taMax3Low: Int,
    @field:Json(name = "taMax3High") val taMax3High: Int,

    @field:Json(name = "taMin4") val taMin4: Int,
    @field:Json(name = "taMin4Low") val taMin4Low: Int,
    @field:Json(name = "taMin4High") val taMin4High: Int,
    @field:Json(name = "taMax4") val taMax4: Int,
    @field:Json(name = "taMax4Low") val taMax4Low: Int,
    @field:Json(name = "taMax4High") val taMax4High: Int,

    @field:Json(name = "taMin5") val taMin5: Int,
    @field:Json(name = "taMin5Low") val taMin5Low: Int,
    @field:Json(name = "taMin5High") val taMin5High: Int,
    @field:Json(name = "taMax5") val taMax5: Int,
    @field:Json(name = "taMax5Low") val taMax5Low: Int,
    @field:Json(name = "taMax5High") val taMax5High: Int,

    @field:Json(name = "taMin6") val taMin6: Int,
    @field:Json(name = "taMin6Low") val taMin6Low: Int,
    @field:Json(name = "taMin6High") val taMin6High: Int,
    @field:Json(name = "taMax6") val taMax6: Int,
    @field:Json(name = "taMax6Low") val taMax6Low: Int,
    @field:Json(name = "taMax6High") val taMax6High: Int,

    @field:Json(name = "taMin7") val taMin7: Int,
    @field:Json(name = "taMin7Low") val taMi73Low: Int,
    @field:Json(name = "taMin7High") val taMin7High: Int,
    @field:Json(name = "taMax7") val taMax7: Int,
    @field:Json(name = "taMax7Low") val taMax7Low: Int,
    @field:Json(name = "taMax7High") val taMax7High: Int,
)

