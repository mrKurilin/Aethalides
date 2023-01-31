package com.mrkurilin.aethalides.data.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class Day(
    val epochDay: Long,
    val dateString: String = LocalDate.ofEpochDay(epochDay).format(
        DateTimeFormatter.ofLocalizedDate(
            FormatStyle.LONG
        )
    ),
    val points: List<Point> = listOf(),
    val events: List<Event> = listOf(),
    val caloriesCount: Int = 0,
    val moneyCount: Int = 0,
)