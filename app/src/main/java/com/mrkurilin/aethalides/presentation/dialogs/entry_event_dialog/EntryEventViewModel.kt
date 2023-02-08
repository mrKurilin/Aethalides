package com.mrkurilin.aethalides.presentation.dialogs.entry_event_dialog

import android.app.Application
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.EntryItemAndroidViewModel
import com.mrkurilin.aethalides.data.util.EntryState
import com.mrkurilin.aethalides.data.util.LocalDateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime

class EntryEventViewModel(app: Application) : EntryItemAndroidViewModel<Event>(app) {

    private val eventsRepository = aethalidesApp.provideEventsRepository()
    val currentLocalTimeFlow = MutableStateFlow<LocalTime>(LocalTime.now())
    val currentLocalDateFlow = MutableStateFlow<LocalDate>(LocalDate.now())

    fun doneButtonPressed(
        eventName: String,
        dateText: String,
        timeText: String,
        isAnnually: Boolean,
    ) {
        val event = getEvent(
            eventName,
            dateText,
            timeText,
            isAnnually,
        )
        when (entryStateFlow.value) {
            is EntryState.Add -> {
                addEvent(event)
            }
            is EntryState.Edit -> {
                updateEvent(event)
            }
        }
    }

    private fun getEvent(
        eventName: String,
        dateText: String,
        timeText: String,
        isAnnually: Boolean,
    ): Event {
        val localDate = LocalDateUtil.fromString(dateText)
        return valueToEdit?.copy(
            name = eventName,
            year = localDate.year,
            month = localDate.monthValue,
            dayOfMonth = localDate.dayOfMonth,
            isAnnually = isAnnually,
            timeText = timeText,
            epochDay = localDate.toEpochDay(),
        ) ?: Event(
            name = eventName,
            localDate = localDate,
            timeText = timeText,
            isAnnually = isAnnually
        )
    }

    private fun updateEvent(event: Event) {
        eventsRepository.updateEvent(event)
    }

    private fun addEvent(event: Event) {
        eventsRepository.addEvent(event)
    }
}