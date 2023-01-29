package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity.Companion.NAME_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity.Companion.TABLE_NAME
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity.Companion.UTC_EPOCH_SECOND_COLUMN_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [
        UTC_EPOCH_SECOND_COLUMN_NAME,
        NAME_COLUMN_NAME,
    ]
)
data class EventRoomEntity(
    @ColumnInfo(name = NAME_COLUMN_NAME) val name: String,
    @ColumnInfo(name = YEAR_COLUMN_NAME) val year: Int,
    @ColumnInfo(name = MONTH_COLUMN_NAME) val month: Int,
    @ColumnInfo(name = DAY_COLUMN_NAME) val day: Int,
    @ColumnInfo(name = IS_EVERY_YEAR_COLUMN_NAME) val isEveryYear: Boolean,
    @ColumnInfo(name = UTC_EPOCH_SECOND_COLUMN_NAME) val utcEpochSecond: Long = 0,
) {

    fun toEvent(): Event {
        return Event(
            name = name,
            year = year,
            month = month,
            day = day,
            isEveryYear = isEveryYear,
            utcEpochSecond = utcEpochSecond,
        )
    }

    companion object {

        const val TABLE_NAME = "events"
        const val NAME_COLUMN_NAME = "name"
        const val YEAR_COLUMN_NAME = "year"
        const val MONTH_COLUMN_NAME = "month"
        const val DAY_COLUMN_NAME = "day"
        const val IS_EVERY_YEAR_COLUMN_NAME = "is_every_year"
        const val UTC_EPOCH_SECOND_COLUMN_NAME = "UTC_epoch_second"

        fun fromEvent(event: Event): EventRoomEntity {
            return EventRoomEntity(
                name = event.name,
                year = event.year,
                month = event.month,
                day = event.day,
                isEveryYear = event.isEveryYear,
                utcEpochSecond = event.utcEpochSecond,
            )
        }
    }
}