package com.mrkurilin.aethalides.data.model

data class Point(
    val planEpochSeconds: Long,
    val description: String,
    val color: Int,
    val isDone: Boolean = false,
    val tag: String = "",
    val planEpochDays: Long = planEpochSeconds / 60 / 60 / 24
)