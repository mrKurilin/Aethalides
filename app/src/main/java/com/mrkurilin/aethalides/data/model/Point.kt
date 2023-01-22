package com.mrkurilin.aethalides.data.model

data class Point(
    val color: Int,
    val planEpochSeconds: Long,
    val description: String,
    val isDone: Boolean,
    val tag: String,
    val planEpochDays: Long = planEpochSeconds / 60 / 60 / 24
)