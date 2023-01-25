package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.graphics.Color
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import java.time.Month

class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val dayOfMonthTextView: TextView = view.findViewById(R.id.day_number_text_view)
    private val pointsGridLayout: GridLayout = view.findViewById(R.id.points_grid_layout)
    private val gridLayoutChildren = pointsGridLayout.children.toList()
    private var color: Int = Color.WHITE

    fun bind(dayNumber: String, isToday: Boolean, points: List<Int>, month: Month) {
        dayOfMonthTextView.text = dayNumber

        if (isToday) {
            itemView.setBackgroundColor(Color.RED)
        } else if (month.value % 2 == 0) {
            itemView.setBackgroundColor(Color.WHITE)
        } else {
            itemView.setBackgroundColor(Color.GRAY)
        }

        for (i in gridLayoutChildren.indices) {
            color = try {
                points[i]
            } catch (e: IndexOutOfBoundsException) {
                Color.TRANSPARENT
            }
            gridLayoutChildren[i].setBackgroundColor(color)
        }
    }
}