package com.mrkurilin.aethalides.presentation.main

import androidx.recyclerview.widget.RecyclerView

abstract class CalendarDaysAdapter<T : CalendarDayViewHolder> : RecyclerView.Adapter<T>() {

    abstract fun setMiddleVisiblePosition(middle: Int): Unit
}