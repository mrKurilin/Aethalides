package com.mrkurilin.aethalides.data.util

import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class LocalDateUtil {

    companion object {

        fun fromTextView(textView: TextView): LocalDate {
            return LocalDate.parse(
                textView.text.toString(),
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            )
        }

        fun fromString(string: String): LocalDate {
            return LocalDate.parse(
                string,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            )
        }

        fun toString(localDate: LocalDate): String {
            return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        }
    }
}