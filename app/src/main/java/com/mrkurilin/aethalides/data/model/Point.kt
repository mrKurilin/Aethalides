package com.mrkurilin.aethalides.data.model

import android.graphics.Color

data class Point(
    val planEpochDay: Long,
    val description: String,
    val color: Int = Color.RED,
    val isDone: Boolean = false,
    val tag: String = "",
    val needToRemind: Boolean = true,
    val planEpochSecond: Long = planEpochDay * 24 * 60 * 60,
    val remindTime: Long = planEpochSecond
)