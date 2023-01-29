package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.EventsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.PointsRecyclerViewAdapter

class MainDayViewHolder(
    view: View,
    deletePoint: (Point) -> Unit,
    editPoint: (Point) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private lateinit var dayTextView: TextView
    private lateinit var moneyCountTextView: TextView
    private lateinit var kcalCountTextView: TextView
    private lateinit var eventsTextView: TextView
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var toDoListTextView: TextView
    private lateinit var toDoListRecyclerView: RecyclerView
    private val eventsRecyclerViewAdapter = EventsRecyclerViewAdapter()
    private val pointsRecyclerViewAdapter = PointsRecyclerViewAdapter(deletePoint, editPoint)

    init {
        initViews()
        setRecyclerViewAdapters()
    }

    fun bind(spending: Int, calories: Int, events: List<Event>, points: List<Point>) {
        moneyCountTextView.text = spending.toString()
        kcalCountTextView.text = calories.toString()
        eventsRecyclerViewAdapter.setItems(events)
        pointsRecyclerViewAdapter.setItems(points)
    }

    private fun initViews() {
        dayTextView = itemView.findViewById(R.id.date_text_view)
        moneyCountTextView = itemView.findViewById(R.id.money_text_view)
        kcalCountTextView = itemView.findViewById(R.id.kcal_text_view)
        eventsTextView = itemView.findViewById(R.id.events_text_view)
        eventsRecyclerView = itemView.findViewById(R.id.events_recycler_view)
        toDoListTextView = itemView.findViewById(R.id.to_do_text_view)
        toDoListRecyclerView = itemView.findViewById(R.id.to_do_recycler_view)
    }

    private fun setRecyclerViewAdapters() {
        eventsRecyclerView.adapter = eventsRecyclerViewAdapter
        toDoListRecyclerView.adapter = pointsRecyclerViewAdapter
    }
}