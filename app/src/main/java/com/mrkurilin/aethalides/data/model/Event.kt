package com.mrkurilin.aethalides.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Event(
    val name: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val isEveryYear: Boolean = false,
    val utcEpochSecond: Long = 0,
    val epochDay: Long = LocalDate.of(year, month, day).toEpochDay(),
) : Parcelable