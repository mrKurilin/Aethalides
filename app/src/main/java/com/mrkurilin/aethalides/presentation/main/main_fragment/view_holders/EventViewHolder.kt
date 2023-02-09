package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event

class EventViewHolder(
    view: View,
    val deleteEvent: (Event) -> Unit,
    val editEvent: (Event) -> Unit,
) : AbstractEventViewHolder(view) {

    private val timeTextView: TextView = view.findViewById(R.id.time_text_view)
    private val eventNameTextView: TextView = view.findViewById(R.id.event_text_view)
    private val moreButton: ImageButton = view.findViewById(R.id.more_button)
    private lateinit var event: Event

    init {
        moreButton.setOnClickListener {
            showPopupMenu()
        }
    }

    override fun bind(event: Event) {
        this.event = event
        timeTextView.text = event.timeText
        eventNameTextView.text = event.name
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(itemView.context, moreButton)
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