package com.mrkurilin.aethalides.data.repository.roomRepositories

import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.repository.NotesRepository
import com.mrkurilin.aethalides.data.room.NotesDao
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity

class NotesRoomRepository(
    private val notesDao: NotesDao
) : NotesRepository {

    override fun addNote(note: Note) {
        notesDao.addNote(NoteRoomEntity.fromNote(note))
    }

    override fun getNotesListByDate(epochDay: Long): List<Note> {
        return notesDao.getNotesListByDate(epochDay).map { noteRoomEntity ->
            noteRoomEntity.toNote()
        }
    }

    override fun deleteNote(note: Note) {
        notesDao.deleteNote(NoteRoomEntity.fromNote(note))
    }

    override fun updateNote(note: Note) {
        notesDao.updateNote(NoteRoomEntity.fromNote(note))
    }
}