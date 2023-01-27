package com.mrkurilin.aethalides.presentation.main.to_do_list_fragment

import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.EpochSecondsUtil
import com.mrkurilin.aethalides.presentation.views.ToDoItemView

class ToDoDayViewHolder(
    view: View,
    private val updatePointListener: (Point) -> Unit
) : RecyclerView.ViewHolder(view) {

    private var currentDayTextView: TextView = itemView.findViewById(R.id.date)

    fun bind(currentDay: String, points: List<Point>) {
        clearAllToDoItems()

        currentDayTextView.text = currentDay

        points.forEach { point ->
            if (itemView is LinearLayout) {
                addToDoItemView(point)
            }
        }
    }

    private fun clearAllToDoItems() {
        (itemView as LinearLayout).children.forEach { view ->
            if (view !is TextView) {
                (itemView as LinearLayout).removeView(view)
            }
        }
    }

    private fun addToDoItemView(point: Point) {
        val toDoItemView = ToDoItemView(itemView.context, null)
        toDoItemView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        toDoItemView.setIsDone(point.isDone)
        toDoItemView.setDescription(point.description)

        toDoItemView.setTime(
            EpochSecondsUtil.epochSecondsToHoursAndMinutesString(
                point.planEpochSeconds
            )
        )

        toDoItemView.setOnCheckboxClickListener {
            updatePointListener(point)
        }

        (itemView as LinearLayout).addView(toDoItemView)
    }
}