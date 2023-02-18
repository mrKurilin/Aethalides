package com.mrkurilin.aethalides.data.model

import java.time.LocalDateTime
import java.time.ZoneOffset

data class EatenFood(
    val name: String,
    val kCalCount: Int,
    val eatenFoodMeasure: String,
    val eatenFoodCount: Int,
    val utcEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val epochDay: Long = utcEpochSecond / 60 / 60 / 24,
)