package com.mrkurilin.aethalides.data.util

import com.mrkurilin.aethalides.R
import java.time.Month

class ColorOfMonthUtil {

    companion object {

        fun getColorId(month: Month): Int {
            return when (month) {
                Month.JANUARY -> R.color.JANUARY
                Month.FEBRUARY -> R.color.FEBRUARY
                Month.MARCH -> R.color.MARCH
                Month.APRIL -> R.color.APRIL
                Month.MAY -> R.color.MAY
                Month.JUNE -> R.color.JUNE
                Month.JULY -> R.color.JULY
                Month.AUGUST -> R.color.AUGUST
                Month.SEPTEMBER -> R.color.SEPTEMBER
                Month.OCTOBER -> R.color.OCTOBER
                Month.NOVEMBER -> R.color.NOVEMBER
                Month.DECEMBER -> R.color.DECEMBER
            }
        }
    }
}