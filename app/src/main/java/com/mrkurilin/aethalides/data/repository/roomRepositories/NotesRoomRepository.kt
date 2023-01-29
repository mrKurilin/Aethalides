package com.mrkurilin.aethalides.data.repository.roomRepositories

import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.repository.NotesRepository
import com.mrkurilin.aethalides.data.room.daos.NotesDao
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRoomRepository(
    private val notesDao: NotesDao
) : NotesRepository {

    override fun addNote(note: Note) {
        notesDao.addNote(NoteRoomEntity.fromNote(note))
    }

    override fun getNotesListByDate(epochDay: Long): Flow<List<Note>> {
        return notesDao.getNotesListFlowByDate(epochDay).map { list ->
            list.map { noteRoomEntity -> noteRoomEntity.toNote() }
        }
    }

    override fun deleteNote(note: Note) {
        notesDao.deleteNote(NoteRoomEntity.fromNote(note))
    }

    override fun updateNote(note: Note) {
        notesDao.updateNote(NoteRoomEntity.fromNote(note))
    }
}