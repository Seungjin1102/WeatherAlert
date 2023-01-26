package com.example.weatheralert.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDay(): Int {
    val current = LocalDateTime.now()
    return current.format(DateTimeFormatter.ISO_DATE).replace("-", "").toInt()
}


fun getCurrentTime(): String {
    val current = LocalDateTime.now()
    val formatted = current.format(DateTimeFormatter.ISO_LOCAL_TIME).replace(":", "").substring(0, 2) + "00"
    var time = 0
    val timeList = listOf(200, 500, 800, 1100, 1400, 1700, 2000, 2300)

    timeList.forEach {
        if (formatted.toInt() >= it) time = it
    }

    return if (time.toString().length < 4) "0$time"
    else time.toString()
}



