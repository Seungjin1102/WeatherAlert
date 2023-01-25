package com.example.domain.model

data class WeatherEntity(
    val date: String, //날짜
    val time: String, //시간
    var tmp: String, //기온
    var pop: String, //강수 확률
    var sky: String //하늘 상태 코드
)
