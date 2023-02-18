package com.mrkurilin.aethalides.presentation.dialogs.entry_point_dialog

import android.app.Application
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.repository.PointsRepository
import com.mrkurilin.aethalides.data.util.EntryItemAndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class EntryPointViewModel(app: Application) : EntryItemAndroidViewModel<Point>(app) {

    private val pointsRepository: PointsRepository = aethalidesApp.providePointsRepository()
    var currentLocalDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    var currentLocalTime: MutableStateFlow<LocalTime> = MutableStateFlow(LocalTime.now())

    fun doneButtonPressed(
        description: String,
        localDate: LocalDate,
        localTime: LocalTime,
    ) {
        pointsRepository.addPoint(
            Point(
                description = description,
                localDateTime = LocalDateTime.of(localDate, localTime),
            )
        )
    }
}