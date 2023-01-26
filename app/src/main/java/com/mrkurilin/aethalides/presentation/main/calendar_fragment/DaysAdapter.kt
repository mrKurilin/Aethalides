package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.NumbersToStringUtil
import java.time.LocalDate
import java.time.Month
import java.time.temporal.WeekFields
import java.util.*

class DaysAdapter(
    private val visibleYearListener: VisibleYearListener,
    private val visibleMonthListener: VisibleMonthListener
) : RecyclerView.Adapter<DayViewHolder>() {

    private var pointsColorsOfEpochDays = mutableMapOf<Long, List<Int>>()

    private val today = LocalDate.now()
    private val firstDayOfCurrentWeek = today.with(
        WeekFields.of(Locale.FRANCE).dayOfWeek(), 1
    )

    private var visibleMonth: Month = today.month
    private var visibleYear: Int = today.year

    private val emptyList: List<Int> = emptyList()

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
        val currentPositionDay = firstDayOfCurrentWeek.plusDays(diff)
        val currentPositionEpochDay = currentPositionDay.toEpochDay()

        holder.bind(
            NumbersToStringUtil.getStringFromInt(currentPositionDay.dayOfMonth),
            currentPositionDay.isEqual(today),
            pointsColorsOfEpochDays[currentPositionEpochDay] ?: emptyList,
            currentPositionDay.month
        )
    }

    fun setItems(pointsColorsOfEpochDays: MutableMap<Long, List<Int>>) {
        this.pointsColorsOfEpochDays = pointsColorsOfEpochDays
        notifyDataSetChanged()
    }

    fun setMiddleVisiblePosition(middle: Int) {
        val diff = middle - Int.MAX_VALUE / 2L
        val currentPositionDay = firstDayOfCurrentWeek.plusDays(diff)
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