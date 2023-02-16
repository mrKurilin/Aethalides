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

open class PointViewHolder(
    view: View
) : RecyclerView.ViewHolder(view), Binding {

    private val moreButton: ImageButton = view.findViewById(R.id.more_button)
    private val isDoneCheckBox: CheckBox = view.findViewById(R.id.is_done_checkbox)
    private val timeTextView: TextView = view.findViewById(R.id.time_text_view)
    private val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
    lateinit var point: Point
        private set

    override fun bind(item: Any) {
        if (item !is Point) {
            return
        }

        this.point = item

        isDoneCheckBox.isChecked = item.isDone

        timeTextView.text = EpochSecondsUtil.epochSecondsToHoursAndMinutesString(
            item.planEpochSecond
        )

        descriptionTextView.text = item.description
    }

    fun setOnMoreButtonClickListener(listener: View.OnClickListener) {
        moreButton.setOnClickListener(listener)
    }
}