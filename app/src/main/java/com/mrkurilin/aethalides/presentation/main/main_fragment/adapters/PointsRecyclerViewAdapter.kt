package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.PointViewHolder

class PointsRecyclerViewAdapter(
    private val deletePoint: (Point) -> Unit,
    private val editPoint: (Point) -> Unit
) : RecyclerView.Adapter<PointViewHolder>() {

    private var points: List<Point> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_point, parent, false)
        return PointViewHolder(
            view,
            deletePoint = deletePoint,
            editPoint = editPoint,
        )
    }

    override fun getItemCount(): Int {
        return points.size
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(points[position])
    }

    fun setItems(points: List<Point>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(
                this.points,
                points,
            )
        )
        this.points = points
        diffResult.dispatchUpdatesTo(this)
    }
}