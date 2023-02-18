package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity.Companion.EPOCH_DAY
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity.Companion.TABLE_NAME
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity.Companion.TIME_COLUMN_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [EPOCH_DAY, TIME_COLUMN_NAME]
)
data class NoteRoomEntity(
    @ColumnInfo(name = EPOCH_DAY) val epochDay: Long,
    @ColumnInfo(name = TIME_COLUMN_NAME) val epochSecond: Long,
    @ColumnInfo(name = TEXT_COLUMN_NAME) val text: String
) {

    fun toNote(): Note {
        return Note(epochSecond, text, epochDay)
    }

    companion object {

        const val TABLE_NAME = "notes"
        const val TIME_COLUMN_NAME = "time"
        const val TEXT_COLUMN_NAME = "text"
        const val EPOCH_DAY = "notes_epoch_day"

        fun fromNote(note: Note): NoteRoomEntity {
            return NoteRoomEntity(
                epochDay = note.epochDay,
                epochSecond = note.epochSecond,
                text = note.text
            )
        }
    }
}