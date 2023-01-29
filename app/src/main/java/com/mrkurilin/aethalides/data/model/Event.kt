package com.mrkurilin.aethalides.data.model

data class Event(
    val name: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val isEveryYear: Boolean,
    val utcEpochSecond: Long = 0,
)