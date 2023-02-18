package com.mrkurilin.aethalides.data.util

import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class LocalDateUtil {

    companion object {

        fun localDateFromTextView(textView: TextView): LocalDate {
            return LocalDate.parse(
                textView.text.toString(),
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            )
        }

        fun localDateFromDateString(string: String): LocalDate {
            return LocalDate.parse(
                string,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            )
        }

        fun localDateToString(localDate: LocalDate): String {
            return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        }
    }
}