package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    fun addNote(note: NoteRoomEntity): Unit

    @Query(
        "SELECT * FROM ${NoteRoomEntity.TABLE_NAME} " +
                "WHERE ${NoteRoomEntity.EPOCH_DAY} = :epochDay"
    )
    fun getNotesListFlowByEpochDay(epochDay: Long): Flow<List<NoteRoomEntity>>

    @Update
    fun updateNote(note: NoteRoomEntity): Unit

    @Delete
    fun deleteNote(note: NoteRoomEntity): Unit
}