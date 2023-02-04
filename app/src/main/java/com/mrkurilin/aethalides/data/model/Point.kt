package com.mrkurilin.aethalides.data.model

import android.graphics.Color
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Point constructor(
    val planEpochDay: Long,
    val description: String,
    val color: Int = Color.TRANSPARENT,
    val isDone: Boolean,
    val tag: String,
    val needToRemind: Boolean,
    val planEpochSecond: Long,
    val remindTime: Long,
    val dayOfWeek: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val isDaily: Boolean = false,
    val isWeekly: Boolean = false,
    val isMonthly: Boolean = false,
    val isAnnually: Boolean = false,
) {

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
    )
}