package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Day
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.MainDayViewHolder
import java.time.LocalDate

class MainDaysAdapter(
    private val deletePoint: (Point) -> Unit,
    private val updatePoint: (Point) -> Unit,
    private val deleteEvent: (Event) -> Unit,
    private val updateEvent: (Event) -> Unit,
) : RecyclerView.Adapter<MainDayViewHolder>() {

    private var map: Map<Long, Day> = emptyMap()
    private val currentEpochDay = LocalDate.now().toEpochDay()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_main_day, parent, false)
        return MainDayViewHolder(view, deletePoint, updatePoint, deleteEvent, updateEvent)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: MainDayViewHolder, position: Int) {
        val diffToCurrentDay = position - Int.MAX_VALUE / 2L
        val currentPositionEpochDay = currentEpochDay + diffToCurrentDay

        val currentDay = map[currentPositionEpochDay] ?: Day(currentPositionEpochDay)

        holder.bind(currentDay)
    }

    fun setItems(map: Map<Long, Day>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(
                this.map.values.toList(),
                map.values.toList(),
            )
        )
        this.map = map
        diffResult.dispatchUpdatesTo(this)
    }
}