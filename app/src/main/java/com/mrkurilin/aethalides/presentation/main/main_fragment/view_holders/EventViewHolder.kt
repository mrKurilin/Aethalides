package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.Binding

class EventViewHolder(
    view: View
) : RecyclerView.ViewHolder(view), Binding<Event> {

    private val timeTextView: TextView = view.findViewById(R.id.time_text_view)
    private val eventNameTextView: TextView = view.findViewById(R.id.event_text_view)
    private val moreButton: ImageButton = view.findViewById(R.id.more_button)
    lateinit var event: Event
        private set

    override fun bind(item: Event) {
        this.event = item
        timeTextView.text = item.timeText
        eventNameTextView.text = item.name
    }

    fun setOnMoreButtonClickListener(listener: View.OnClickListener) {
        moreButton.setOnClickListener(listener)
    }
}