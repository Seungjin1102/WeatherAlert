package com.example.data.model.weather

data class MidSkyWeatherResponse(
    val response: Response
) {
    data class Response(
        val header: Header,
        val body: Body
    )

    data class Header(
        val resultCode: Int,
        val resultMsg: String
    )

    data class Body(
        val dataType: String,
        val items: Items,
        val pageNo: Int,
        val numOfRows: Int,
        val totalCount: Int
    )

    data class Items(
        val item: List<Item>
    )

    data class Item(
        val regId: String,
        val rnSt3Am: Int,
        val rnSt3Pm: Int,
        val rnSt4Am: Int,
        val rnSt4Pm: Int,
        val rnSt5Am: Int,
        val rnSt5Pm: Int,
        val rnSt6Am: Int,
        val rnSt6Pm: Int,
        val rnSt7Am: Int,
        val rnSt7Pm: Int,
        val rnSt8: Int,
        val rnSt9: Int,
        val rnSt10: Int,
        val wf3Am: String,
        val wf3Pm: String,
        val wf4Am: String,
        val wf4Pm: String,
        val wf5Am: String,
        val wf5Pm: String,
        val wf6Am: String,
        val wf6Pm: String,
        val wf7Am: String,
        val wf7Pm: String,
        val wf8: String,
        val wf9: String,
        val wf10: String
    )
}

