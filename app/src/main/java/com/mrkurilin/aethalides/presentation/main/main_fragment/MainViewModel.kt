package com.mrkurilin.aethalides.presentation.main.main_fragment

import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.R
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.util.Models
import com.mrkurilin.aethalides.data.util.NavigationConstants.Companion.EPOCH_DAY_KEY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val pointsRepository = aethalidesApp.providePointsRepository()
    private val eventsRepository = aethalidesApp.provideEventsRepository()
    private val spendingRepository = aethalidesApp.provideSpendingRepository()
    private val eatenFoodRepository = aethalidesApp.provideEatenFoodRepository()

    val currentShownDayFlow = MutableStateFlow(LocalDate.now())
    val pointsFlow = MutableStateFlow<List<Point>>(listOf())
    val eventsFlow = MutableStateFlow<List<Event>>(listOf())
    val spendingFlow = MutableStateFlow(0)
    val caloriesCountFlow = MutableStateFlow(0)

    private var job: Job? = null

    init {
        viewModelScope.launch {
            currentShownDayFlow.collect { localDate ->
                job?.cancel()
                job = observeRepositories(localDate)
            }
        }
    }

    private suspend fun observeRepositories(
        localDate: LocalDate,
    ): Job = viewModelScope.launch {
        val epochDay = localDate.toEpochDay()

        combine(
            eventsRepository.getEventsListFlowByLocalDate(localDate),
            pointsRepository.getPointsListFlowByLocalDate(localDate),
            spendingRepository.getSpendingFlowByEpochDay(epochDay),
            eatenFoodRepository.getCaloriesFlowByEpochDay(epochDay),
        ) { events, points, spending, caloriesCount ->
            eventsFlow.value = events
            pointsFlow.value = points
            spendingFlow.value = spending ?: 0
            caloriesCountFlow.value = caloriesCount ?: 0
        }.collect()
    }

    fun deletePoint(point: Point) {
        pointsRepository.deletePoint(point)
    }

    fun editPoint(point: Point, navController: NavController) {
        val action = MainFragmentDirections.actionMainFragmentToEntryPointDialogFragment()
        action.point = point
        navController.navigate(action)
    }

    fun deleteEvent(event: Event) {
        eventsRepository.deleteEvent(event)
    }

    fun editEvent(event: Event, navController: NavController) {
        val action = MainFragmentDirections.actionMainFragmentToEntryEventDialogFragment()
        action.event = event
        navController.navigate(action)
    }

    fun epochDaySelected(epochDay: Long) {
        currentShownDayFlow.value = LocalDate.ofEpochDay(epochDay)
    }

    fun addItemPressed(modelToAdd: Models, navController: NavController) {
        val action = when (modelToAdd) {
            Models.EatenFood -> {
                R.id.action_mainFragment_to_entryEatenFoodDialogFragment
            }
            Models.Event -> {
                R.id.action_mainFragment_to_entryEventDialogFragment
            }
            Models.Note -> {
                R.id.action_mainFragment_to_entryNoteDialogFragment
            }
            Models.Point -> {
                R.id.action_mainFragment_to_entryPointDialogFragment
            }
            Models.Spending -> {
                R.id.action_mainFragment_to_entrySpendingDialogFragment
            }
        }
        navController.navigate(
            action,
            bundleOf(EPOCH_DAY_KEY to currentShownDayFlow.value.toEpochDay())
        )
    }

    fun updatePoint(point: Point) {
        pointsRepository.updatePoint(point)
    }
}