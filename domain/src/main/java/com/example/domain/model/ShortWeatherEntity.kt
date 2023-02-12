package com.example.domain.model

data class ShortWeatherEntity(
    val date: String, //날짜
    val time: String, //시간
    var tmp: String, //기온
    var pop: String, //강수 확률
    var sky: String, //하늘 상태 코드(1: 맑음, 3: 구름많음, 4: 흐림)
    var pty: String, //강수 형태(0: 없음, 1: 비, 2: 비/눈, 3: 눈, 4: 소나기)
)
