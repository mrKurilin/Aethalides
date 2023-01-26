package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

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

//        addPointsToTest()
    }

    private fun addPointsToTest() {
        pointsRepository.deletePointByTag("")
        val today = LocalDate.now().toEpochDay()

        repeat(1200) {
            val randomDay = Random.nextLong(today - 700, today + 700)
            val randomTime = randomDay * 24 * 60 * 60 - Random.nextInt(100)
            pointsRepository.addPoint(
                Point(
                    randomTime,
                    "Test ${Random.nextInt(1200)}",
                    Color.rgb(Random.nextInt(254), Random.nextInt(254), Random.nextInt(254))
                )
            )
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