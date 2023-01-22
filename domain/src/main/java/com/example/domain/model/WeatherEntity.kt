package com.example.domain.model

data class WeatherEntity(
    val date: String, //날짜
    val time: String, //시간
    val tmp: String, //기온
    val pop: String, //강수 확률
    val sky: String //하늘 상태 코드
)
