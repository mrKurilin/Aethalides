package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Note

interface NotesRepository {

    fun addNote(note: Note): Unit

    fun getNotesListByDate(epochDay: Long): List<Note>

    fun deleteNote(note: Note): Unit

    fun updateNote(note: Note): Unit
}