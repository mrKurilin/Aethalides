package com.mrkurilin.aethalides.data.util

import android.widget.TextView
import java.time.LocalDate

class EpochDayUtil {

    companion object {

        fun getEpochDayFromDateTextView(textView: TextView): Long {
            return LocalDateUtil.localDateFromTextView(textView).toEpochDay()
        }

        fun epochDayToDateString(epochDay: Long): String {
            return LocalDateUtil.localDateToString(LocalDate.ofEpochDay(epochDay))
        }
    }
}