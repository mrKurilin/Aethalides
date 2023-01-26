package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.aethalides.AethalidesApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalendarViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val pointsRepository = aethalidesApp.providePointsRepository()
    private var epochDaysFromDb: Flow<List<Long>> = pointsRepository.getAllPlanDatesFromDb()
    private val pointColorsOfEpochDays = MutableStateFlow<MutableMap<Long, List<Int>>>(
        mutableMapOf()
    )

    init {
        viewModelScope.launch {
            epochDaysFromDb.collect() { epochDaysFromDbList ->
                fillPointColorsOfEpochDays(epochDaysFromDbList)
            }
        }
    }

    suspend fun observePointsColors(observer: (MutableMap<Long, List<Int>>) -> Unit) {
        pointColorsOfEpochDays.collect {
            observer(it)
        }
    }

    private fun fillPointColorsOfEpochDays(epochDaysFromDbList: List<Long>) {
        pointColorsOfEpochDays.update { map ->
            map.clear()
            epochDaysFromDbList.forEach { epochDay ->
                map[epochDay] = pointsRepository.getAllPointsColorsByEpochDay(epochDay)
            }
            return@update map
        }
    }
}