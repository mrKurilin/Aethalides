package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.PointViewHolder

class PointsRecyclerViewAdapter : RecyclerView.Adapter<PointViewHolder>() {

    private var points: List<Point> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_point, parent, false)
        return PointViewHolder(view)
    }

    override fun getItemCount(): Int {
        return points.size
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(points[position])
    }

    fun setItems(points: List<Point>) {
        this.points = points
        notifyDataSetChanged()
    }
}