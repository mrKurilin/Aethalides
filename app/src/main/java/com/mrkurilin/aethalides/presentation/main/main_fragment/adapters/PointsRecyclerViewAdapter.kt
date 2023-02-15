package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.DiffUtilCallback
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.AbstractPointViewHolder
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.PointViewHolder

class PointsRecyclerViewAdapter(
    private val deletePoint: (Point) -> Unit,
    private val editPoint: (Point) -> Unit
) : RecyclerView.Adapter<AbstractPointViewHolder>() {

    private var points: List<Point> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractPointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_point, parent, false)
        val pointViewHolder = PointViewHolder(view)

        pointViewHolder.setOnMoreButtonClickListener { moreButton ->
            showPopupMenu(
                parent.context,
                moreButton,
                pointViewHolder.point
            )
        }

        return pointViewHolder
    }

    override fun getItemCount(): Int {
        return points.size
    }

    override fun onBindViewHolder(holder: AbstractPointViewHolder, position: Int) {
        if (points.isNotEmpty()) {
            holder.bind(points[position])
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

    private fun showPopupMenu(
        context: Context,
        anchor: View,
        point: Point
    ) {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.inflate(R.menu.edit_delete_popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    editPoint(point)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete -> {
                    deletePoint(point)
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