package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import java.time.Month

interface VisibleMonthListener {

    fun onVisibleMonthChanged(month: Month): Unit
}