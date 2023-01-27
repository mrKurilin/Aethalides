package com.mrkurilin.aethalides.data.util

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
    }
}