package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.aethalides.AethalidesApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val pointsRepository = aethalidesApp.providePointsRepository()
    val pointsColorsOfEpochDaysMapFlow = MutableStateFlow<Map<Long, List<Int>>>(
        mapOf()
    )

    init {
        viewModelScope.launch {
            pointsRepository.getEpochDaysToPointsColorsMapFlow().collect { map ->
                pointsColorsOfEpochDaysMapFlow.value = map
            }
        }
    }
}