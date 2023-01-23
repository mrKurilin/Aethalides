package com.mrkurilin.aethalides.data.room

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity

@Dao
interface NotesDao {

    @Query("SELECT * FROM ${RoomConstants.NOTES_TABLE_NAME} WHERE ${RoomConstants.NOTES_DATE_COLUMN_NAME} = :epochDay")
    fun getNotesListByDate(epochDay: Long): List<NoteRoomEntity>

    @Insert
    fun addNote(note: NoteRoomEntity): Unit

    @Delete
    fun deleteNote(note: NoteRoomEntity): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: NoteRoomEntity): Unit
}