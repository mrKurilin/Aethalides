package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.data.model.Point

abstract class AbstractPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(point: Point)
}