package com.mrkurilin.aethalides.data.model

import java.time.LocalDateTime
import java.time.ZoneOffset

data class Spending(
    val name: String,
    val cost: Float,
    val utcEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val epochDay: Long = utcEpochSecond / 60 / 60 / 24
)