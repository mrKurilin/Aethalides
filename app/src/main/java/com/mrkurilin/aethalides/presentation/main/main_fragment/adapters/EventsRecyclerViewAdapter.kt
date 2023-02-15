package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.AbstractEventViewHolder
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.EventViewHolder

class EventsRecyclerViewAdapter(
    private val deleteEvent: (Event) -> Unit,
    private val editEvent: (Event) -> Unit,
) : RecyclerView.Adapter<AbstractEventViewHolder>() {

    var events: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_event, parent, false)
        val eventViewHolder = EventViewHolder(view)
        eventViewHolder.setOnMoreButtonClickListener { moreButton ->
            showPopupMenu(
                parent.context,
                moreButton,
                eventViewHolder.event
            )
        }
        return eventViewHolder
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: AbstractEventViewHolder, position: Int) {
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

    private fun showPopupMenu(
        context: Context,
        anchor: View,
        event: Event
    ) {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.inflate(R.menu.edit_delete_popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    editEvent(event)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete -> {
                    deleteEvent(event)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }
        popupMenu.show()
    }
}