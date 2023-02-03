package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Relation
import com.mrkurilin.aethalides.data.model.Day

@Entity
data class DayRoomEntity(
    @ColumnInfo(name = EPOCH_DAY_COLUMN_NAME) val day_epoch_day: Long,

    @Relation(
        entity = PointRoomEntity::class,
        parentColumn = "day_epoch_day",
        entityColumn = PointRoomEntity.EPOCH_DAY_COLUMN_NAME
    )
    val points: List<PointRoomEntity> = listOf(),

    @Relation(
        entity = EventRoomEntity::class,
        parentColumn = "day_epoch_day",
        entityColumn = EventRoomEntity.EPOCH_DAY_COLUMN_NAME
    )
    val events: List<EventRoomEntity> = listOf(),

    @Relation(
        entity = EatenFoodRoomEntity::class,
        parentColumn = "day_epoch_day",
        entityColumn = EventRoomEntity.EPOCH_DAY_COLUMN_NAME
    )
    val caloriesCount: Int? = 0,

    @Relation(
        entity = SpendingRoomEntity::class,
        parentColumn = "day_epoch_day",
        entityColumn = EventRoomEntity.EPOCH_DAY_COLUMN_NAME
    )
    val moneyCount: Int? = 0,
) {

    fun toDay(): Day {
        return Day(
            epochDay = day_epoch_day,
            points = points.map { pointRoomEntity ->
                pointRoomEntity.toPoint()
            },
            events = events.map { eventRoomEntity ->
                eventRoomEntity.toEvent()
            },
            caloriesCount = caloriesCount ?: 0,
            moneyCount = moneyCount ?: 0
        )
    }

    companion object {

        const val EPOCH_DAY_COLUMN_NAME = "day_epoch_day"
    }
}