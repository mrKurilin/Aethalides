package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.repository.PointsRepository
import java.time.LocalDate
import java.time.Month
import java.time.temporal.WeekFields
import java.util.*

class DaysAdapter(
    private val repository: PointsRepository,
    private val visibleYearListener: VisibleYearListener,
    private val visibleMonthListener: VisibleMonthListener
) : RecyclerView.Adapter<DayViewHolder>() {

    private val today = LocalDate.now()
    private val firstDayOfWeekFromCurrentDay = today.with(
        WeekFields.of(Locale.FRANCE).dayOfWeek(), 1
    )

    private val numbers = mutableMapOf<Int, String>()

    private var middleVisiblePosition: Int = Int.MAX_VALUE / 2
    private var visibleMonth: Month = today.month
    private var visibleYear: Int = today.year

    init {
        for (i in 1..31) {
            numbers[i] = i.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.day_view_holder, parent, false)
        return DayViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val diff = position - Int.MAX_VALUE / 2L
        val currentPositionDay = firstDayOfWeekFromCurrentDay.plusDays(diff)
        val currentPositionEpochDay = currentPositionDay.toEpochDay()

        val pointsColors = repository.getPointsListByDate(currentPositionEpochDay).map { it.color }

        holder.bind(
            numbers[currentPositionDay.dayOfMonth]!!,
            today.isEqual(currentPositionDay),
            pointsColors,
            currentPositionDay.month
        )
    }

    fun setMiddleVisiblePosition(middle: Int) {
        val diff = middle - Int.MAX_VALUE / 2L
        val currentPositionDay = firstDayOfWeekFromCurrentDay.plusDays(diff)
        middleVisiblePosition = middle
        if (visibleMonth != currentPositionDay.month) {
            visibleMonth = currentPositionDay.month
            visibleMonthListener.onVisibleMonthChanged(currentPositionDay.month)
        }
        if (visibleYear != currentPositionDay.year) {
            visibleYear = currentPositionDay.year
            visibleYearListener.onVisibleYearListener(currentPositionDay.year)
        }
    }
}