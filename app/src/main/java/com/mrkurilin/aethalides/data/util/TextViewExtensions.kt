package com.mrkurilin.aethalides.data.util

import android.graphics.Color
import android.graphics.Paint
import android.widget.TextView

class TextViewExtensions {

    companion object {

        fun TextView.setNotCrossedOutBlackColoredText() {
            this.setTextColor(Color.BLACK)
            this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        fun TextView.setCrossedOutGrayColoredText() {
            this.setTextColor(Color.GRAY)
            this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }
}