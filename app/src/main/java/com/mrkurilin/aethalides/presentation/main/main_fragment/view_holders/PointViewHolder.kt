package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.EpochSecondsUtil

class PointViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var isDoneCheckBox: CheckBox
    private lateinit var timeTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var moreButton: ImageButton

    init {
        initViews()

        moreButton.setOnClickListener {
            // TODO: Popup menu
        }
    }

    fun bind(point: Point) {
        isDoneCheckBox.isChecked = point.isDone

        descriptionTextView.text = point.description

        timeTextView.text = EpochSecondsUtil.epochSecondsToHoursAndMinutesString(
            point.planEpochSecond
        )
    }

    private fun initViews() {
        isDoneCheckBox = itemView.findViewById(R.id.is_done_checkbox)
        timeTextView = itemView.findViewById(R.id.time_text_view)
        descriptionTextView = itemView.findViewById(R.id.description_text_view)
        moreButton = itemView.findViewById(R.id.more_button)
    }
}