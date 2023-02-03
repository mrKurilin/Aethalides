package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.data.model.Event

abstract class AbstractEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(event: Event)
}