package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val navController = aethalidesApp.provideNavController()
    private val pointsRepository = aethalidesApp.providePointsRepository()
    private val eventsRepository = aethalidesApp.provideEventsRepository()
    private val spendingRepository = aethalidesApp.provideSpendingRepository()
    private val eatenFoodRepository = aethalidesApp.provideEatenFoodRepository()

    private val currentShownDay = MutableStateFlow(LocalDate.now())

    val pointsFlow = MutableStateFlow<List<Point>>(listOf())
    val eventsFlow = MutableStateFlow<List<Event>>(listOf())

    private var job: Job? = null

    init {
        viewModelScope.launch {
            currentShownDay.collect { localDate ->
                job?.cancel()
                job = launch {
                    launch {
                        eventsRepository.getEventsListFlowByEpochDay(localDate.toEpochDay())
                            .collect { list ->
                                eventsFlow.value = list
                            }
                    }

                    launch {
                        pointsRepository.getPointsListFlowByEpochDay(localDate.toEpochDay())
                            .collect { list ->
                                pointsFlow.value = list
                            }
                    }
                }
            }
        }
    }

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
        val action = MainFragmentDirections.actionMainFragmentToEntryEventDialogFragment()
        action.event = event
        navController.navigate(action)
    }

    fun addPointPressed() {
        navController.navigate(R.id.action_mainFragment_to_entryPointDialogFragment)
    }

    fun addEatenFoodPressed() {
        navController.navigate(R.id.action_mainFragment_to_entryEatenFoodDialogFragment)
    }

    fun addSpendingPressed() {
        navController.navigate(R.id.action_mainFragment_to_entrySpendingDialogFragment)
    }

    fun addNotePressed() {
        navController.navigate(R.id.action_mainFragment_to_entryNoteDialogFragment)
    }

    fun addEventPressed() {
        navController.navigate(R.id.action_mainFragment_to_entryEventDialogFragment)
    }
}