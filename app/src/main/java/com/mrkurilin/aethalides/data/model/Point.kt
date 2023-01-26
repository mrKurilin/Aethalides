package com.mrkurilin.aethalides.data.model

import java.time.LocalDateTime
import java.time.ZoneOffset

data class Point(
    val planEpochSeconds: Long,
    val description: String,
    val color: Int,
    val isDone: Boolean = false,
    val tag: String = "",
    val planEpochDays: Long = LocalDateTime.ofEpochSecond(planEpochSeconds, 0, ZoneOffset.UTC).toLocalDate().toEpochDay()
)