package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.EpochSecondsUtil

class EventViewHolder(
    view: View,
    val deleteEvent: (Event) -> Unit,
    val editEvent: (Event) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private val timeTextView: TextView = view.findViewById(R.id.time_text_view)
    private val eventTextView: TextView = view.findViewById(R.id.event_text_view)
    private val moreButton: ImageButton = view.findViewById(R.id.more_button)
    private lateinit var event: Event

    init {
        moreButton.setOnClickListener {
            showPopupMenu()
        }
    }

    fun bind(event: Event) {
        this.event = event
        timeTextView.text = EpochSecondsUtil.epochSecondsToHoursAndMinutesString(
            event.utcEpochSecond
        )
        eventTextView.text = event.name
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(itemView.context, itemView)
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