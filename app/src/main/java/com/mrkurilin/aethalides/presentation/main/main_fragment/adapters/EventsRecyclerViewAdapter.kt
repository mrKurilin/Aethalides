package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.EventViewHolder

class EventsRecyclerViewAdapter(
    private val deleteEvent: (Event) -> Unit,
    private val editEvent: (Event) -> Unit,
) : RecyclerView.Adapter<EventViewHolder>() {

    var events: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_event, parent, false)
        return EventViewHolder(view, deleteEvent, editEvent)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun setItems(events: List<Event>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(
                this.events,
                events,
            )
        )
        this.events = events
        diffResult.dispatchUpdatesTo(this)
    }
}