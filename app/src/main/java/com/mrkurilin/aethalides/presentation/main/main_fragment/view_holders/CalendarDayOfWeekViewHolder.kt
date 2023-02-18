package com.mrkurilin.aethalides.presentation.main.main_fragment.view_holders

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.util.CalendarDayViewHolder

class CalendarDayOfWeekViewHolder(view: View) : CalendarDayViewHolder(view) {

    private val dayOfWeekTextView: TextView = view.findViewById(R.id.day_of_week)
    private val dayOfMonthTextView: TextView = view.findViewById(R.id.day_of_month)
    private val arrowDownImageView: ImageView = view.findViewById(R.id.arrow_down_image_view)
    private val arrowUpImageView: ImageView = view.findViewById(R.id.arrow_up_image_view)
    var epochDay: Long = 0
        private set

    fun bind(
        epochDay: Long,
        dayOfWeeK: String,
        dayOfMonth: String,
        colorStateList: ColorStateList,
        isShownDay: Boolean
    ) {
        this.epochDay = epochDay
        dayOfWeekTextView.text = dayOfWeeK
        dayOfMonthTextView.text = dayOfMonth
        itemView.backgroundTintList = colorStateList
        arrowUpImageView.isVisible = isShownDay
        arrowDownImageView.isVisible = isShownDay
    }
}