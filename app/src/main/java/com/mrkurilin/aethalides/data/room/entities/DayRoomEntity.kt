package com.mrkurilin.aethalides.data.room.entities

import androidx.room.Entity
import androidx.room.Relation

@Entity
data class DayRoomEntity(
    val day_epoch_day: Long,

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

    val caloriesCount: Int? = 0,
    val moneyCount: Int? = 0,
) {

    companion object {

        const val EPOCH_DAY_COLUMN_NAME = "day_epoch_day"
    }
}