package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.TextView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.CalendarDayViewHolder

class CalendarDayOfWeekViewHolder(view: View) : CalendarDayViewHolder(view) {

    private val dayOfWeekTextView: TextView = view.findViewById(R.id.day_of_week)
    private val dayOfMonthTextView: TextView = view.findViewById(R.id.day_of_month)

    fun bind(
        dayOfWeeK: String,
        dayOfMonth: String
    ) {
        dayOfWeekTextView.text = dayOfWeeK
        dayOfMonthTextView.text = dayOfMonth
    }
}