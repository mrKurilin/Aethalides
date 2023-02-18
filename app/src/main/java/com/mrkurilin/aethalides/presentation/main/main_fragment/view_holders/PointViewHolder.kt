package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.Binding
import com.mrkurilin.aethalides.data.util.EpochSecondsUtil
import com.mrkurilin.aethalides.data.util.TextViewExtensions.Companion.setCrossedOutGrayColoredText
import com.mrkurilin.aethalides.data.util.TextViewExtensions.Companion.setNotCrossedOutBlackColoredText

open class PointViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view), Binding<Point> {

    private val moreButton: ImageButton = view.findViewById(R.id.more_button)
    private val isDoneCheckBox: CheckBox = view.findViewById(R.id.is_done_checkbox)
    private val timeTextView: TextView = view.findViewById(R.id.time_text_view)
    private val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
    private lateinit var point: Point

    override fun bind(item: Point) {
        point = item

        isDoneCheckBox.isChecked = point.isDone

        timeTextView.text = EpochSecondsUtil.epochSecondsToHoursAndMinutesString(
            point.planEpochSecond
        )

        descriptionTextView.text = point.description

        if (point.isDone) {
            descriptionTextView.setCrossedOutGrayColoredText()
        } else {
            descriptionTextView.setNotCrossedOutBlackColoredText()
        }
    }

    fun setOnMoreButtonClickListener(listener: (View, Point) -> Unit) {
        moreButton.setOnClickListener { moreButton ->
            listener(moreButton, point)
        }
    }

    fun setOnCheckBoxClickListener(function: (Point) -> Unit) {
        isDoneCheckBox.setOnClickListener {
            function(point)
        }
    }
}