package com.mrkurilin.aethalides.data.model

import android.os.Parcelable
import com.mrkurilin.aethalides.data.util.LocalTimeUtil
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Event(
    val id: Long = 0,
    val name: String,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val dayOfWeek: Int,
    val isAnnually: Boolean = false,
    val timeText: String = "",
    val epochDay: Long = LocalDate.of(year, month, dayOfMonth).toEpochDay(),
) : Parcelable {

    constructor(
        name: String,
        localDate: LocalDate,
        localTime: LocalTime,
        isAnnually: Boolean,
        id: Long = 0
    ) : this(
        name = name,
        year = localDate.year,
        month = localDate.monthValue,
        dayOfMonth = localDate.dayOfMonth,
        dayOfWeek = localDate.dayOfWeek.value,
        isAnnually = isAnnually,
        timeText = LocalTimeUtil.toString(localTime),
        id = id
    )
}