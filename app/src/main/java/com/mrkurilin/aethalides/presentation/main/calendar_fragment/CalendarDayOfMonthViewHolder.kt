package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.graphics.Color
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.CalendarDayViewHolder
import java.time.Month

class CalendarDayOfMonthViewHolder(view: View) : CalendarDayViewHolder(view) {

    private val dayOfMonthTextView: TextView = view.findViewById(R.id.day_number_text_view)
    private val pointsGridLayout: GridLayout = view.findViewById(R.id.points_grid_layout)
    private val gridLayoutChildren = pointsGridLayout.children.toList()
    private var color: Int = Color.WHITE

    fun bind(dayNumber: String, isToday: Boolean, pointsColors: List<Int>, month: Month) {
        dayOfMonthTextView.text = dayNumber

        if (isToday) {
            itemView.setBackgroundColor(Color.YELLOW)
        } else if (month.value % 2 == 0) {
            itemView.setBackgroundColor(Color.WHITE)
        } else {
            itemView.setBackgroundColor(Color.GRAY)
        }

        for (i in gridLayoutChildren.indices) {
            try {
                color = pointsColors[i]
                gridLayoutChildren[i].visibility = View.VISIBLE
                gridLayoutChildren[i].background.setTint(color)
            } catch (e: IndexOutOfBoundsException) {
                gridLayoutChildren[i].visibility = View.GONE
            }
        }
    }
}