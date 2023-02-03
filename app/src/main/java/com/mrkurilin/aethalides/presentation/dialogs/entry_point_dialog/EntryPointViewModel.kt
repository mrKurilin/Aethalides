package com.mrkurilin.aethalides.presentation.dialogs.entry_point_dialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mrkurilin.aethalides.AethalidesApp
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.repository.PointsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class EntryPointViewModel(app: Application) : AndroidViewModel(app) {

    private val aethalidesApp = app as AethalidesApp
    private val pointsRepository: PointsRepository = aethalidesApp.providePointsRepository()
    var currentLocalDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    var currentLocalTime: MutableStateFlow<LocalTime> = MutableStateFlow(LocalTime.now())

    fun addPoint(point: Point) {
        pointsRepository.addPoint(point)
    }

    fun updatePoint(point: Point) {
        pointsRepository.updatePoint(point)
    }

    fun doneButtonPressed(
        description: String,
        localDate: LocalDate,
        localTime: LocalTime
    ) {
        pointsRepository.addPoint(
            Point(
                description = description,
                localDateTime = LocalDateTime.of(localDate, localTime),
            )
        )
    }
}