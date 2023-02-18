package com.mrkurilin.aethalides.data.util

import android.widget.TextView
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeUtil {

    companion object {

        fun localTimeFromTextView(
            textView: TextView
        ): LocalTime {
            return LocalTime.parse(
                textView.text.toString(),
                DateTimeFormatter.ofPattern("HH:mm")
            )
        }

        fun localTimeFromString(
            string: String
        ): LocalTime {
            return LocalTime.parse(
                string,
                DateTimeFormatter.ofPattern("HH:mm")
            )
        }

        fun toString(localTime: LocalTime): String {
            return localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}