package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.AbstractPointViewHolder
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.NoPointsViewHolder
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.PointViewHolder

class PointsRecyclerViewAdapter(
    private val deletePoint: (Point) -> Unit,
    private val editPoint: (Point) -> Unit
) : RecyclerView.Adapter<AbstractPointViewHolder>() {

    private var points: List<Point> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractPointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.view_holder_no_points -> {
                val view = inflater.inflate(R.layout.view_holder_no_points, parent, false)
                NoPointsViewHolder(view)
            }
            R.layout.view_holder_point -> {
                val view = inflater.inflate(R.layout.view_holder_point, parent, false)
                return PointViewHolder(
                    view,
                    deletePoint = deletePoint,
                    editPoint = editPoint,
                )
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (points.isEmpty()) {
            1
        } else {
            points.size
        }
    }

    override fun onBindViewHolder(holder: AbstractPointViewHolder, position: Int) {
        if (points.isNotEmpty()) {
            holder.bind(points[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (points.isEmpty()) {
            R.layout.view_holder_no_points
        } else {
            R.layout.view_holder_point
        }
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