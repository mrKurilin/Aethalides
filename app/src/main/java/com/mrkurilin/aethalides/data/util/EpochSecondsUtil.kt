package com.mrkurilin.aethalides.data.util

import android.widget.TextView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class EpochSecondsUtil {

    companion object {

        fun epochSecondsToHoursAndMinutesString(epochSeconds: Long): String {
            val localDateTime = LocalDateTime.ofEpochSecond(
                epochSeconds, 0, ZoneOffset.UTC
            )
            return "${localDateTime.hour}:${localDateTime.minute}"
        }

        fun fromTimeTextViewAndLocalDate(
            textView: TextView,
            localDate: LocalDate
        ): Long {
            return LocalTimeUtil.fromTextView(textView).toEpochSecond(
                localDate,
                ZoneOffset.UTC
            )
        }
    }
}