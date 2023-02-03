package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.AbstractEventViewHolder
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.EventViewHolder
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.NoEventsViewHolder

class EventsRecyclerViewAdapter(
    private val deleteEvent: (Event) -> Unit,
    private val editEvent: (Event) -> Unit,
) : RecyclerView.Adapter<AbstractEventViewHolder>() {

    var events: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.view_holder_no_events -> {
                val view = inflater.inflate(R.layout.view_holder_no_events, parent, false)
                NoEventsViewHolder(view)
            }
            R.layout.view_holder_event -> {
                val view = inflater.inflate(R.layout.view_holder_event, parent, false)
                EventViewHolder(
                    view,
                    deleteEvent = deleteEvent,
                    editEvent = editEvent
                )
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (events.isEmpty()) {
            1
        } else {
            events.size
        }
    }

    override fun onBindViewHolder(holder: AbstractEventViewHolder, position: Int) {
        if (events.isNotEmpty()) {
            holder.bind(events[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (events.isEmpty()) {
            R.layout.view_holder_no_events
        } else {
            R.layout.view_holder_event
        }
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