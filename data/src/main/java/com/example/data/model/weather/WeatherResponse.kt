package com.example.data.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class WeatherResponse<T>(
    @field:Json(name = "response") val response: Response<T>
) {
    data class Response<T>(
        @field:Json(name = "header") val header: Header,
        @field:Json(name = "body") val body: Body<T>
    )

    data class Header(
        @field:Json(name = "resultCode") val resultCode: Int,
        @field:Json(name = "resultMsg") val resultMsg: String,
    )

    data class Body<T>(
        @field:Json(name = "dataType") val dataType: String,
        @field:Json(name = "items") val items: Items<T>,
        @field:Json(name = "pageNo") val pageNo: Int,
        @field:Json(name = "numOfRows") val numOfRows: Int,
        @field:Json(name = "totalCount") val totalCount: Int,
    )

    data class Items<T>(
        @field:Json(name = "item") val item: List<T>
    )
}
