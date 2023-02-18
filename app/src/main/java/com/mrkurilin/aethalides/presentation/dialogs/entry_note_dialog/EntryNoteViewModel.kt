package com.mrkurilin.aethalides.presentation.dialogs.entry_note_dialog

import android.app.Application
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.util.EntryItemAndroidViewModel
import com.mrkurilin.aethalides.data.util.EntryState

class EntryNoteViewModel(
    app: Application
) : EntryItemAndroidViewModel<Note>(app) {

    private val notesRepository = aethalidesApp.provideNotesRepository()

    fun doneButtonPressed(text: String) {
        when (entryStateFlow.value) {
            is EntryState.Add -> {
                addNote(text)
            }
            is EntryState.Edit -> {
                updateNote(text)
            }
        }
    }

    private fun addNote(text: String) {
        notesRepository.addNote(
            Note(
                text = text
            )
        )
    }

    private fun updateNote(text: String) {
        valueToEdit?.copy(text = text)?.let {
            notesRepository.updateNote(
                note = it
            )
        }
    }
}