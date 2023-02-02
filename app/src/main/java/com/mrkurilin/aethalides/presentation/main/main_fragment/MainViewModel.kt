package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Day
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.flow.Flow

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()
    private val daysRepository = aethalidesApp.provideDaysRepository()
    private val pointsRepository = aethalidesApp.providePointsRepository()
    private val eventsRepository = aethalidesApp.provideEventsRepository()

    val daysToEpochDaysMapFlow: Flow<List<Day>> = daysRepository.getDaysToEpochDaysMapFlow()

    fun deletePoint(point: Point) {
        pointsRepository.deletePoint(point)
    }

    fun editPoint(point: Point) {
        // TODO: startDialog
    }

    fun deleteEvent(event: Event) {
        eventsRepository.deleteEvent(event)
    }

    fun editEvent(event: Event) {
        // TODO: startDialog
    }

    fun addPointPressed() {
        navController.navigate(R.id.action_mainFragment_to_addPointDialog)
    }
}