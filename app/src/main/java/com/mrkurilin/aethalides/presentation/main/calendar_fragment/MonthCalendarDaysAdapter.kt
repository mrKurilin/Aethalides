package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.NumbersToStringUtil
import com.mrkurilin.aethalides.presentation.main.CalendarDaysAdapter
import java.time.LocalDate
import java.time.Month
import java.time.temporal.WeekFields
import java.util.*

class MonthCalendarDaysAdapter(
    private val onVisibleYearChanged: (String) -> Unit,
    private val onVisibleMonthChanged: (String) -> Unit
) : CalendarDaysAdapter<CalendarDayOfMonthViewHolder>() {

    private var pointsColorsOfEpochDays = mapOf<Long, List<Int>>()

    private val today = LocalDate.now()
    private val firstDayOfCurrentWeek = today.with(
        WeekFields.of(Locale.FRANCE).dayOfWeek(), 1
    )

    private var visibleMonth: Month = today.month
    private var visibleYear: Int = today.year

    private val emptyList: List<Int> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayOfMonthViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_day_of_month, parent, false)
        return CalendarDayOfMonthViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: CalendarDayOfMonthViewHolder, position: Int) {
        val diffToCurrentDay = position - Int.MAX_VALUE / 2L
        val currentPositionDay = firstDayOfCurrentWeek.plusDays(diffToCurrentDay)
        val currentPositionEpochDay = currentPositionDay.toEpochDay()

        holder.bind(
            NumbersToStringUtil.getStringFromInt(currentPositionDay.dayOfMonth),
            currentPositionDay.isEqual(today),
            pointsColorsOfEpochDays[currentPositionEpochDay] ?: emptyList,
            currentPositionDay.month
        )
    }

    fun setItems(pointsColorsOfEpochDays: Map<Long, List<Int>>) {
        this.pointsColorsOfEpochDays = pointsColorsOfEpochDays
        notifyDataSetChanged()
    }

    override fun setMiddleVisiblePosition(middle: Int) {
        val diff = middle - Int.MAX_VALUE / 2L
        val currentPositionDay = firstDayOfCurrentWeek.plusDays(diff)
        if (visibleMonth != currentPositionDay.month) {
            visibleMonth = currentPositionDay.month
            onVisibleMonthChanged(currentPositionDay.month.toString())
        }
        if (visibleYear != currentPositionDay.year) {
            visibleYear = currentPositionDay.year
            onVisibleYearChanged(currentPositionDay.year.toString())
        }
    }
}