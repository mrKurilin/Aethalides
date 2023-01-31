package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.EpochSecondsUtil

class PointViewHolder(
    view: View,
    val deletePoint: (Point) -> Unit,
    val editPoint: (Point) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private lateinit var isDoneCheckBox: CheckBox
    private lateinit var timeTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var moreButton: ImageButton
    private lateinit var point: Point

    init {
        initViews()

        moreButton.setOnClickListener {
            showPopupMenu()
        }
    }

    fun bind(point: Point) {
        this.point = point

        isDoneCheckBox.isChecked = point.isDone

        timeTextView.text = EpochSecondsUtil.epochSecondsToHoursAndMinutesString(
            point.planEpochSecond
        )

        descriptionTextView.text = point.description
    }

    private fun initViews() {
        isDoneCheckBox = itemView.findViewById(R.id.is_done_checkbox)
        timeTextView = itemView.findViewById(R.id.time_text_view)
        descriptionTextView = itemView.findViewById(R.id.description_text_view)
        moreButton = itemView.findViewById(R.id.more_button)
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(itemView.context, itemView)
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