package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.mrkurilin.aethalides.data.model.Day
import com.mrkurilin.aethalides.data.room.RoomConstants

class DayRoomEntity(
    @ColumnInfo(name = RoomConstants.EPOCH_DAY)
    var day_epoch_day: Long? = 0,

    @Relation(
        entity = PointRoomEntity::class,
        parentColumn = "day_epoch_day",
        entityColumn = PointRoomEntity.EPOCH_DAY
    )
    var points: List<PointRoomEntity>? = listOf(),

    @Relation(
        entity = EventRoomEntity::class,
        parentColumn = "day_epoch_day",
        entityColumn = EventRoomEntity.EPOCH_DAY
    )
    var events: List<EventRoomEntity>? = listOf(),

    var caloriesCount: Int? = 0,
    var moneyCount: Int? = 0,
) {

    fun toDay(): Day {
        return Day(
            epochDay = day_epoch_day ?: 0,
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
}