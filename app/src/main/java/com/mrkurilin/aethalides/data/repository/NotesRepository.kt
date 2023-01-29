package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun addNote(note: Note): Unit

    fun getNotesListByDate(epochDay: Long): Flow<List<Note>>

    fun deleteNote(note: Note): Unit

    fun updateNote(note: Note): Unit
}