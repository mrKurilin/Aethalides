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
    private val editPoint: (Point) -> Unit
) : ItemsRecyclerView<Point, PointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_point, parent, false)
        val pointViewHolder = PointViewHolder(view)

        pointViewHolder.setOnMoreButtonClickListener { moreButton ->
            EditDeletePopupMenu(
                context = parent.context,
                anchor = moreButton,
                item = pointViewHolder.point,
                startEditItem = editPoint,
                deleteItem = deletePoint
            ).show()
        }

        return pointViewHolder
    }
}