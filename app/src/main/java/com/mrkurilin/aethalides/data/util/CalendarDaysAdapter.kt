package com.mrkurilin.aethalides.data.util

import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

abstract class CalendarDaysAdapter<T : CalendarDayViewHolder> : RecyclerView.Adapter<T>() {

    abstract fun setMiddleVisiblePosition(middle: Int): Unit

    fun getAdapterPositionByEpochDay(oldEpochDay: Long): Int {
        val todayEpochDay = LocalDate.now().toEpochDay()
        val diff = todayEpochDay - oldEpochDay
        return (Int.MAX_VALUE / 2) - diff.toInt()
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}