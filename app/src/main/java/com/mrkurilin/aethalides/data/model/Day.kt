package com.mrkurilin.aethalides.data.model

data class Day(
    val date: String,
    val points: MutableList<Point> = mutableListOf(),
)