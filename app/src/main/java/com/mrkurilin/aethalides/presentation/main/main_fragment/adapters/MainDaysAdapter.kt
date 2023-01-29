package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.MainDayViewHolder

class MainDaysAdapter : RecyclerView.Adapter<MainDayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_main_day, parent, false)
        return MainDayViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: MainDayViewHolder, position: Int) {
        holder.bind()
    }
}