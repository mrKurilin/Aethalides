package com.mrkurilin.aethalides.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity

@Dao
interface NotesDao {

    @Query("SELECT * FROM ${RoomConstants.NOTES_TABLE_NAME} WHERE ${RoomConstants.NOTES_DATE_COLUMN_NAME} = :date")
    fun getNotesListByDate(date: String): List<NoteRoomEntity>

    @Insert
    fun addNote(note: NoteRoomEntity): Unit

    @Delete
    fun deletePoint(note: NoteRoomEntity): Unit
}