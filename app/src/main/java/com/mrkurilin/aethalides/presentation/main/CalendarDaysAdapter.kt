package com.mrkurilin.aethalides.presentation.main

import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

abstract class CalendarDaysAdapter<T : CalendarDayViewHolder> : RecyclerView.Adapter<T>() {

    abstract fun setMiddleVisiblePosition(middle: Int): Unit

    fun getAdapterPositionByEpochDay(oldEpochDay: Long): Int {
        val todayEpochDay = LocalDate.now().toEpochDay()
        val diff = todayEpochDay - oldEpochDay
        return (Int.MAX_VALUE / 2) - diff.toInt()
    }
}