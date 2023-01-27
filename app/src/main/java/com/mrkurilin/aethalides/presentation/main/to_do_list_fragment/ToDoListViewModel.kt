package com.mrkurilin.aethalides.presentation.main.to_do_list_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ToDoListViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val pointsRepository = aethalidesApp.providePointsRepository()
    val epochDaysToPointsFlow: MutableStateFlow<Map<Long, List<Point>>> = MutableStateFlow(
        mapOf()
    )

    init {
        viewModelScope.launch {
            pointsRepository.getEpochDaysToPointsMapFlow().collect { map ->
                epochDaysToPointsFlow.value = map
            }
        }
    }

    fun updatePoint(point: Point) {
        pointsRepository.updatePoint(point)
    }
}