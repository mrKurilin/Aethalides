package com.mrkurilin.aethalides.presentation.main.main_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.EditDeletePopupMenu
import com.mrkurilin.aethalides.data.util.ItemsRecyclerView
import com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders.PointViewHolder

class PointsRecyclerViewAdapter(
    private val deletePoint: (Point) -> Unit,
    private val editPoint: (Point) -> Unit,
    private val updatePoint: (Point) -> Unit,
) : ItemsRecyclerView<Point, PointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_point, parent, false)
        val pointViewHolder = PointViewHolder(view)

        pointViewHolder.setOnMoreButtonClickListener { moreButton, point ->
            EditDeletePopupMenu(
                context = parent.context,
                anchor = moreButton,
                item = point,
                startEditItem = editPoint,
                deleteItem = deletePoint
            ).show()
        }

        pointViewHolder.setOnCheckBoxClickListener { point ->
            val isDone = point.isDone
            val updatedPoint = point.copy(isDone = !isDone)
            updatePoint(updatedPoint)
        }

        return pointViewHolder
    }
}