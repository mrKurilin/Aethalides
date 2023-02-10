package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
)
data class EventRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN_NAME)
    val id: Long,
    @ColumnInfo(name = NAME_COLUMN_NAME) val name: String,
    @ColumnInfo(name = YEAR_COLUMN_NAME) val year: Int,
    @ColumnInfo(name = MONTH_COLUMN_NAME) val month: Int,
    @ColumnInfo(name = DAY_OF_MONTH_COLUMN_NAME) val dayOfMonth: Int,
    @ColumnInfo(name = DAY_OF_WEEK_COLUMN_NAME) val dayOfWeek: Int,
    @ColumnInfo(name = IS_EVERY_YEAR_COLUMN_NAME) val isEveryYear: Boolean,
    @ColumnInfo(name = TIME_TEXT_COLUMN_NAME) val timeText: String,
    @ColumnInfo(name = EPOCH_DAY) val epochDay: Long,
) {

    fun toEvent(): Event {
        return Event(
            id = id,
            name = name,
            year = year,
            month = month,
            dayOfMonth = dayOfMonth,
            dayOfWeek = dayOfWeek,
            isAnnually = isEveryYear,
            timeText = timeText,
            epochDay = epochDay
        )
    }

    companion object {

        const val TABLE_NAME = "events"
        const val ID_COLUMN_NAME = "event_id"
        const val NAME_COLUMN_NAME = "name"
        const val YEAR_COLUMN_NAME = "year"
        const val MONTH_COLUMN_NAME = "month"
        const val DAY_OF_MONTH_COLUMN_NAME = "day_of_month"
        const val DAY_OF_WEEK_COLUMN_NAME = "day_of_week"
        const val IS_EVERY_YEAR_COLUMN_NAME = "is_every_year"
        const val TIME_TEXT_COLUMN_NAME = "event_UTC_epoch_second"
        const val EPOCH_DAY = "event_epoch_day"

        fun fromEvent(event: Event): EventRoomEntity {
            return EventRoomEntity(
                id = event.id,
                name = event.name,
                year = event.year,
                month = event.month,
                dayOfMonth = event.dayOfMonth,
                dayOfWeek = event.dayOfWeek,
                isEveryYear = event.isAnnually,
                timeText = event.timeText,
                epochDay = event.epochDay
            )
        }
    }
}