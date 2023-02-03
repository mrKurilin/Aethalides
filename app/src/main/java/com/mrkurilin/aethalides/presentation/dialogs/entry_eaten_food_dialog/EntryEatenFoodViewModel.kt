package com.mrkurilin.aethalides.presentation.dialogs.entry_eaten_food_dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.data.model.EatenFood
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime

class EntryEatenFoodViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val eatenFoodRepository = aethalidesApp.provideEatenFoodRepository()
    val dateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val timeFlow: MutableStateFlow<LocalTime> = MutableStateFlow(LocalTime.now())

    fun addEatenFood(eatenFood: EatenFood) {
        eatenFoodRepository.addEatenFood(eatenFood)
    }
}