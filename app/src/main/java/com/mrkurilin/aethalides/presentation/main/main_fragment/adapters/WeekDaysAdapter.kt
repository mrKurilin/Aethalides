package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.ColorOfMonthUtil
import com.mrkurilin.aethalides.presentation.main.CalendarDaysAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.CalendarDayOfWeekViewHolder
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*

const val SPACE_BETWEEN_ITEMS = 4 + ((4 + 4) * 6) + 4
const val ITEM_COUNT = 7

class WeekDaysAdapter(
    private val onVisibleYearChanged: (String) -> Unit,
    private val onVisibleMonthChanged: (Month) -> Unit,
    private val onDaySelected: (Long) -> Unit,
) : CalendarDaysAdapter<CalendarDayOfWeekViewHolder>() {

    private var today = LocalDate.now()
    private var visibleMonth: Month = today.month
    private var visibleYear: Int = today.year
    private var currentShownDay = today

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayOfWeekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_day_of_week, parent, false)

        val params = view.layoutParams
        params.width = (parent.measuredWidth - SPACE_BETWEEN_ITEMS) / ITEM_COUNT
        (params as MarginLayoutParams).setMargins(4)
        view.layoutParams = params
        val calendarDayOfWeekViewHolder = CalendarDayOfWeekViewHolder(view)
        calendarDayOfWeekViewHolder.itemView.setOnClickListener {
            val oldEpochDay = currentShownDay.toEpochDay()
            val epochDay = calendarDayOfWeekViewHolder.epochDay
            onDaySelected(epochDay)
            currentShownDay = LocalDate.ofEpochDay(epochDay)
            notifyItemChanged(calendarDayOfWeekViewHolder.adapterPosition)
            notifyItemChanged(getAdapterPositionByEpochDay(oldEpochDay))
        }
        return calendarDayOfWeekViewHolder
    }

    override fun onBindViewHolder(holder: CalendarDayOfWeekViewHolder, position: Int) {
        val diffToCurrentDay = position - Int.MAX_VALUE / 2L
        val currentPositionLocalDate = today.plusDays(diffToCurrentDay)

        val monthColorId = if (currentPositionLocalDate.isEqual(today)) {
            R.color.today_color
        } else {
            ColorOfMonthUtil.getColorId(currentPositionLocalDate.month)
        }

        holder.bind(
            epochDay = currentPositionLocalDate.toEpochDay(),
            dayOfWeeK = currentPositionLocalDate.dayOfWeek.getDisplayName(
                TextStyle.SHORT, Locale.forLanguageTag("ru")
            ),
            dayOfMonth = currentPositionLocalDate.dayOfMonth.toString(),
            colorStateList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    holder.itemView.context,
                    monthColorId
                )
            ),
            isShownDay = currentShownDay.isEqual(currentPositionLocalDate)
        )
    }

    override fun setMiddleVisiblePosition(middle: Int) {
        val diff = middle - Int.MAX_VALUE / 2L
        val currentPositionDay = today.plusDays(diff)
        if (visibleMonth != currentPositionDay.month) {
            visibleMonth = currentPositionDay.month
            onVisibleMonthChanged(
                currentPositionDay.month
            )
        }
        if (visibleYear != currentPositionDay.year) {
            visibleYear = currentPositionDay.year
            onVisibleYearChanged(currentPositionDay.year.toString())
        }
    }
}