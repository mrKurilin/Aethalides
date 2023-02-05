package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.mrkurilin.aethalides.data.model.Day

class DayRoomEntity(
    @ColumnInfo(name = EPOCH_DAY)
    var epoch_day: Long? = 0,

    @Relation(
        entity = PointRoomEntity::class,
        parentColumn = EPOCH_DAY,
        entityColumn = PointRoomEntity.EPOCH_DAY
    )
    var points: List<PointRoomEntity>? = listOf(),

    @Relation(
        entity = EventRoomEntity::class,
        parentColumn = EPOCH_DAY,
        entityColumn = EventRoomEntity.EPOCH_DAY
    )
    var events: List<EventRoomEntity>? = listOf(),

    var caloriesCount: Int? = 0,
    var moneyCount: Int? = 0,
) {

    fun toDay(): Day {
        return Day(
            epochDay = epoch_day ?: 0,
            points = points?.map { pointRoomEntity ->
                pointRoomEntity.toPoint()
            } ?: listOf(),
            events = events?.map { eventRoomEntity ->
                eventRoomEntity.toEvent()
            } ?: listOf(),
            caloriesCount = caloriesCount ?: 0,
            moneyCount = moneyCount ?: 0
        )
    }

    companion object {

        const val EPOCH_DAY = "epoch_day"
    }
}