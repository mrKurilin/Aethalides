package com.mrkurilin.aethalides.data.util

import android.widget.TextView
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeUtil {

    companion object {

        fun fromTextView(
            textView: TextView
        ): LocalTime {
            return LocalTime.parse(
                textView.text.toString(),
                DateTimeFormatter.ofPattern("HH:mm")
            )
        }

        fun toString(localTime: LocalTime): String {
            return localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}