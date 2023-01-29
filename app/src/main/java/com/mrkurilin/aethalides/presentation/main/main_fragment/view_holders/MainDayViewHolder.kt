package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.EventsRecyclerViewAdapter
import com.mrkurilin.aethalides.presentation.main.main_fragment.adapters.PointsRecyclerViewAdapter

class MainDayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var dayTextView: TextView
    private lateinit var moneyCountTextView: TextView
    private lateinit var kcalCountTextView: TextView
    private lateinit var eventsTextView: TextView
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var toDoListTextView: TextView
    private lateinit var toDoListRecyclerView: RecyclerView
    private val eventsRecyclerViewAdapter = EventsRecyclerViewAdapter()
    private val toDoListRecyclerViewAdapter = PointsRecyclerViewAdapter()

    init {
        initViews()
        setRecyclerViewAdapters()
    }

    fun bind() {

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
        toDoListRecyclerView.adapter = toDoListRecyclerViewAdapter
    }
}