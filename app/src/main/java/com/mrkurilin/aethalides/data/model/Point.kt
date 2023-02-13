package com.mrkurilin.aethalides.data.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZoneOffset

@Parcelize
data class Point constructor(
    val planEpochDay: Long,
    val description: String,
    val color: Int,
    val isDone: Boolean,
    val tag: String,
    val needToRemind: Boolean,
    val planEpochSecond: Long,
    val remindTime: Long,
    val dayOfWeek: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val isDaily: Boolean,
    val isWeekly: Boolean,
    val isMonthly: Boolean,
    val isAnnually: Boolean,
    val trackInHistory: Boolean,
) : Parcelable {

    constructor(
        localDateTime: LocalDateTime,
        description: String,
        color: Int = Color.TRANSPARENT,
        tag: String = "",
        needToRemind: Boolean = false,
        remindTime: Long = localDateTime.toEpochSecond(ZoneOffset.UTC),
        isDaily: Boolean = false,
        isWeekly: Boolean = false,
        isMonthly: Boolean = false,
        isAnnually: Boolean = false,
        trackInHistory: Boolean = false,
    ) : this(
        planEpochDay = localDateTime.toLocalDate().toEpochDay(),
        description = description,
        color = color,
        isDone = false,
        tag = tag,
        needToRemind = needToRemind,
        planEpochSecond = localDateTime.toEpochSecond(ZoneOffset.UTC),
        remindTime = remindTime,
        dayOfWeek = localDateTime.dayOfWeek.value,
        dayOfMonth = localDateTime.dayOfMonth,
        month = localDateTime.month.value,
        year = localDateTime.year,
        isDaily = isDaily,
        isWeekly = isWeekly,
        isMonthly = isMonthly,
        isAnnually = isAnnually,
        trackInHistory = trackInHistory,
    )
}