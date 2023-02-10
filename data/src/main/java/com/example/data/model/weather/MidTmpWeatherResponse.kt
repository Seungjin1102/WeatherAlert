package com.example.data.model.weather

import com.squareup.moshi.Json
//
//data class MidTmpWeatherResponse1(
//    val response: Response
//) {
//    data class Response(
//        val header: Header,
//        val body: Body
//    )
//
//    data class Header(
//        val resultCode: Int,
//        val resultMsg: String
//    )
//
//    data class Body(
//        val dataType: String,
//        val items: Items,
//        val pageNo: Int,
//        val numOfRows: Int,
//        val totalCount: Int
//    )
//
//    data class Items(
//        val item: List<Item>
//    )
//
//    data class Item(
//        val regId: String,
//        val taMin3: Int,
//        val taMin3Low: Int,
//        val taMin3High: Int,
//        val taMax3: Int,
//        val taMax3Low: Int,
//        val taMax3High: Int,
//
//        val taMin4: Int,
//        val taMin4Low: Int,
//        val taMin4High: Int,
//        val taMax4: Int,
//        val taMax4Low: Int,
//        val taMax4High: Int,
//
//        val taMin5: Int,
//        val taMin5Low: Int,
//        val taMin5High: Int,
//        val taMax5: Int,
//        val taMax5Low: Int,
//        val taMax5High: Int,
//
//        val taMin6: Int,
//        val taMin6Low: Int,
//        val taMin6High: Int,
//        val taMax6: Int,
//        val taMax6Low: Int,
//        val taMax6High: Int,
//
//        val taMin7: Int,
//        val taMin7Low: Int,
//        val taMin7High: Int,
//        val taMax7: Int,
//        val taMax7Low: Int,
//        val taMax7High: Int,
//
//        val taMin8: Int,
//        val taMin8Low: Int,
//        val taMin8High: Int,
//        val taMax8: Int,
//        val taMax8Low: Int,
//        val taMax8High: Int,
//
//        val taMin9: Int,
//        val taMin9Low: Int,
//        val taMin9High: Int,
//        val taMax9: Int,
//        val taMax9Low: Int,
//        val taMax9High: Int,
//
//        val taMin10: Int,
//        val taMin10Low: Int,
//        val taMin10High: Int,
//        val taMax10: Int,
//        val taMax10Low: Int,
//        val taMax10High: Int,
//    )
//}

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

