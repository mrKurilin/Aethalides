package com.mrkurilin.aethalides.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.room.RoomConstants

@Entity(
    tableName = RoomConstants.NOTES_TABLE_NAME
)
data class NoteRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val time: String,
    val text: String
) {

    fun toNote(): Note {
        return Note(date, time, text)
    }

    companion object {

        fun fromNote(note: Note): NoteRoomEntity {
            return NoteRoomEntity(
                0,
                date = note.date,
                time = note.time,
                text = note.text
            )
        }
    }
}