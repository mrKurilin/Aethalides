package com.mrkurilin.aethalides.data.model

data class Point(
    val planEpochDay: Long,
    val planEpochSecond: Long = planEpochDay * 24 * 60 * 60,
    val description: String,
    val color: Int,
    val isDone: Boolean = false,
    val tag: String = "",
)