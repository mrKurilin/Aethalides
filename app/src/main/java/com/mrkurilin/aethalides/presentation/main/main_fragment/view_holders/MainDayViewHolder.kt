package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Day
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.EventsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.PointsRecyclerViewAdapter

class MainDayViewHolder(
    view: View,
    deletePoint: (Point) -> Unit,
    editPoint: (Point) -> Unit,
    deleteEvent: (Event) -> Unit,
    editEvent: (Event) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private lateinit var dayTextView: TextView
    private lateinit var moneyCountTextView: TextView
    private lateinit var kcalCountTextView: TextView
    private lateinit var eventsTextView: TextView
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var pointsTextView: TextView
    private lateinit var pointsRecyclerView: RecyclerView
    private val eventsRecyclerViewAdapter = EventsRecyclerViewAdapter(deleteEvent, editEvent)
    private val pointsRecyclerViewAdapter = PointsRecyclerViewAdapter(deletePoint, editPoint)

    init {
        initViews()
        setRecyclerViewAdapters()
    }

    fun bind(day: Day) {
        dayTextView.text = day.dateString
        moneyCountTextView.text = itemView.resources.getString(
            R.string.money_count,
            day.moneyCount
        )
        kcalCountTextView.text = itemView.resources.getString(
            R.string.kcal_count,
            day.caloriesCount
        )
        eventsRecyclerViewAdapter.setItems(day.events)
        pointsRecyclerViewAdapter.setItems(day.points)
    }

    private fun initViews() {
        dayTextView = itemView.findViewById(R.id.date_picker_text_view)
        moneyCountTextView = itemView.findViewById(R.id.money_text_view)
        kcalCountTextView = itemView.findViewById(R.id.kcal_text_view)
        eventsTextView = itemView.findViewById(R.id.events_text_view)
        eventsRecyclerView = itemView.findViewById(R.id.events_recycler_view)
        pointsTextView = itemView.findViewById(R.id.points_text_view)
        pointsRecyclerView = itemView.findViewById(R.id.points_recycler_view)
    }

    private fun setRecyclerViewAdapters() {
        eventsRecyclerView.adapter = eventsRecyclerViewAdapter
        pointsRecyclerView.adapter = pointsRecyclerViewAdapter
    }
}