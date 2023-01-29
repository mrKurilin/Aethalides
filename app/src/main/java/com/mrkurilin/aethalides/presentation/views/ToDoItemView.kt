package com.mrkurilin.aethalides.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.mrkurilin.aethalides.R

class ToDoItemView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private lateinit var checkBox: CheckBox
    private lateinit var timeTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var moreButton: ImageButton

    init {
        View.inflate(context, R.layout.view_holder_point, this)
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        initViews()
    }

    fun setIsDone(isDone: Boolean) {
        checkBox.isChecked = isDone
    }

    fun setTime(time: String) {
        timeTextView.text = time
    }

    fun setDescription(description: String) {
        descriptionTextView.text = description
    }

    fun setOnCheckboxClickListener(listener: () -> Unit) {
        checkBox.setOnClickListener {
            listener()
        }
    }

    private fun initViews() {
        checkBox = rootView.findViewById(R.id.is_done_checkbox)
        timeTextView = rootView.findViewById(R.id.time_text_view)
        descriptionTextView = rootView.findViewById(R.id.description_text_view)
        moreButton = rootView.findViewById(R.id.more_button)
    }
}