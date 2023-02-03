package com.mrkurilin.aethalides.data.util

import android.widget.TextView

class EpochDayUtil {

    companion object {

        fun getEpochDayFromDateTextView(textView: TextView): Long {
            return LocalDateUtil.fromTextView(textView).toEpochDay()
        }
    }
}