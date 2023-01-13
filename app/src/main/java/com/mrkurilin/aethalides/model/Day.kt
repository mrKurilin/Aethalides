package com.mrkurilin.aethalides.model

data class Day(
    val date: String,
    val points: MutableList<Point> = mutableListOf(),
)