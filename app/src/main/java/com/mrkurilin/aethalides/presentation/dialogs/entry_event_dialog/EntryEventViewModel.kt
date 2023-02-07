package com.mrkurilin.aethalides.presentation.dialogs.entry_event_dialog

import android.app.Application
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.util.EntryItemAndroidViewModel
import com.mrkurilin.aethalides.data.util.EntryState
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime

class EntryEventViewModel(app: Application) : EntryItemAndroidViewModel<Event>(app) {

    private val eventsRepository = aethalidesApp.provideEventsRepository()
    val currentLocalTimeFlow = MutableStateFlow<LocalTime>(LocalTime.now())
    val currentLocalDateFlow = MutableStateFlow<LocalDate>(LocalDate.now())

    fun doneButtonPressed(text: String) {
        when (entryStateFlow.value) {
            is EntryState.Add -> {
                addEvent(text)
            }
            is EntryState.Edit -> {
                updateEvent()
            }
        }
    }

    private fun updateEvent() {

    }

    fun addEvent(text: String) {
        eventsRepository.addEvent(
            Event(
                name = text,
                year = currentLocalDateFlow.value.year,
                month = currentLocalDateFlow.value.monthValue,
                day = currentLocalDateFlow.value.dayOfMonth,
            )
        )
    }
}