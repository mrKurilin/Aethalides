package com.mrkurilin.aethalides.data.model

import androidx.annotation.ColorInt

data class Point(
    @ColorInt
    val color: Int,
    val description: String,
    val isDone: Boolean,
)