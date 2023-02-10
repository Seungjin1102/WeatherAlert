package com.example.data.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//data class MidSkyWeatherResponse1(
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
//        val rnSt3Am: Int,
//        val rnSt3Pm: Int,
//        val rnSt4Am: Int,
//        val rnSt4Pm: Int,
//        val rnSt5Am: Int,
//        val rnSt5Pm: Int,
//        val rnSt6Am: Int,
//        val rnSt6Pm: Int,
//        val rnSt7Am: Int,
//        val rnSt7Pm: Int,
//        val rnSt8: Int,
//        val rnSt9: Int,
//        val rnSt10: Int,
//        val wf3Am: String,
//        val wf3Pm: String,
//        val wf4Am: String,
//        val wf4Pm: String,
//        val wf5Am: String,
//        val wf5Pm: String,
//        val wf6Am: String,
//        val wf6Pm: String,
//        val wf7Am: String,
//        val wf7Pm: String,
//        val wf8: String,
//        val wf9: String,
//        val wf10: String
//    )
//}

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
    @field:Json(name = "rnSt8") val rnSt8: Int,
    @field:Json(name = "rnSt9") val rnSt9: Int,
    @field:Json(name = "rnSt10") val rnSt10: Int,

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
    @field:Json(name = "wf8") val wf8: String,
    @field:Json(name = "wf9") val wf9: String,
    @field:Json(name = "wf10") val wf10: String,
)

