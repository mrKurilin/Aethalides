package com.mrkurilin.aethalides.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Event(
    val id: Long = 0,
    val name: String,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val isAnnually: Boolean = false,
    val timeText: String = "",
    val epochDay: Long = LocalDate.of(year, month, dayOfMonth).toEpochDay(),
) : Parcelable {

    constructor(
        name: String,
        localDate: LocalDate,
        timeText: String,
        isAnnually: Boolean,
    ) : this(
        name = name,
        year = localDate.year,
        month = localDate.monthValue,
        dayOfMonth = localDate.dayOfMonth,
        isAnnually = isAnnually,
        timeText = timeText,
    )
}