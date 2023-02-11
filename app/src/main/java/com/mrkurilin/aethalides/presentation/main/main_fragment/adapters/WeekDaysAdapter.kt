package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.app.ActionBar.LayoutParams
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.CalendarDaysAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.CalendarDayOfWeekViewHolder
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class WeekDaysAdapter(
    private val onVisibleYearChanged: (String) -> Unit,
    private val onVisibleMonthChanged: (String) -> Unit
) : CalendarDaysAdapter<CalendarDayOfWeekViewHolder>() {

    private var today = LocalDate.now()
    private var visibleMonth: Month = today.month
    private var visibleYear: Int = today.year

    override fun setMiddleVisiblePosition(middle: Int) {
        val diff = middle - Int.MAX_VALUE / 2L
        val currentPositionDay = today.plusDays(diff)
        if (visibleMonth != currentPositionDay.month) {
            visibleMonth = currentPositionDay.month
            onVisibleMonthChanged(currentPositionDay.month.getDisplayName(
                TextStyle.FULL, Locale.getDefault()
            ))
        }
        if (visibleYear != currentPositionDay.year) {
            visibleYear = currentPositionDay.year
            onVisibleYearChanged(currentPositionDay.year.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayOfWeekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val width = parent.measuredWidth / 7
        val view = inflater.inflate(R.layout.view_holder_day_of_week, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(width, LayoutParams.WRAP_CONTENT)
        return CalendarDayOfWeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarDayOfWeekViewHolder, position: Int) {
        val diffToCurrentDay = position - Int.MAX_VALUE / 2L
        val currentPositionLocalDate = today.plusDays(diffToCurrentDay)

        holder.bind(
            currentPositionLocalDate.dayOfWeek.getDisplayName(
                TextStyle.SHORT, Locale.forLanguageTag("ru")
            ),
            currentPositionLocalDate.dayOfMonth.toString()
        )
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}