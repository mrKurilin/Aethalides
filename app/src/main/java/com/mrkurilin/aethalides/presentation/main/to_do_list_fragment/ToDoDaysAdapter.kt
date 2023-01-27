package com.mrkurilin.aethalides.presentation.main.to_do_list_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ToDoDaysAdapter(
    private val onPointUpdated: (Point) -> Unit
) : RecyclerView.Adapter<ToDoDayViewHolder>() {

    private val today = LocalDate.now()
    private var pointsOfEpochDaysMap = emptyMap<Long, List<Point>>()
    private val emptyList: List<Point> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.to_do_day_item_view_holder, parent, false)
        return ToDoDayViewHolder(
            view,
            updatePointListener = { point ->
                onPointUpdated(point)
            }
        )
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ToDoDayViewHolder, position: Int) {
        val diffToCurrentDay = position - Int.MAX_VALUE / 2L
        val currentPositionDay = today.plusDays(diffToCurrentDay)
        val currentPositionEpochDay = currentPositionDay.toEpochDay()

        holder.bind(
            currentPositionDay.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
            pointsOfEpochDaysMap[currentPositionEpochDay] ?: emptyList
        )
    }

    fun setItems(pointsToEpochDaysMap: Map<Long, List<Point>>) {
        this.pointsOfEpochDaysMap = pointsToEpochDaysMap
        notifyDataSetChanged()
    }
}