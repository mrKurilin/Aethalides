package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.room.RoomConstants

@Entity(
    tableName = RoomConstants.NOTES_TABLE_NAME,
    primaryKeys = [RoomConstants.NOTES_DATE_COLUMN_NAME, RoomConstants.NOTES_TIME_COLUMN_NAME]
)
data class NoteRoomEntity(
    @ColumnInfo(name = RoomConstants.NOTES_DATE_COLUMN_NAME) val epochDay: Long,
    @ColumnInfo(name = RoomConstants.NOTES_TIME_COLUMN_NAME) val epochSecond: Long,
    @ColumnInfo(name = RoomConstants.NOTES_TEXT_COLUMN_NAME) val text: String
) {

    fun toNote(): Note {
        return Note(epochSecond, text, epochDay)
    }

    companion object {

        fun fromNote(note: Note): NoteRoomEntity {
            return NoteRoomEntity(
                epochDay = note.epochDay,
                epochSecond = note.epochSecond,
                text = note.text
            )
        }
    }
}