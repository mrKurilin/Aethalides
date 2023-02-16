package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.EditDeletePopupMenu
import com.mrkurilin.aethalides.data.util.ItemsRecyclerView
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.EventViewHolder

class EventsRecyclerViewAdapter(
    private val deleteEvent: (Event) -> Unit,
    private val editEvent: (Event) -> Unit,
) : ItemsRecyclerView<Event, EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_event, parent, false)
        val eventViewHolder = EventViewHolder(view)
        eventViewHolder.setOnMoreButtonClickListener { moreButton ->
            EditDeletePopupMenu(
                context = parent.context,
                anchor = moreButton,
                item = eventViewHolder.event,
                startEditItem = editEvent,
                deleteItem = deleteEvent
            ).show()
        }
        return eventViewHolder
    }
}