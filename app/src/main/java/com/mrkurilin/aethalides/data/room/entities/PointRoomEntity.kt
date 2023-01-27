package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.RoomConstants

@Entity(
    tableName = RoomConstants.POINTS_TABLE_NAME,
    primaryKeys = [
        RoomConstants.POINTS_PLAN_TIME_COLUMN_NAME,
        RoomConstants.POINTS_DESCRIPTION_COLUMN_NAME
    ],
    indices = [
        Index(value = [RoomConstants.POINTS_PLAN_TIME_COLUMN_NAME, RoomConstants.POINTS_DESCRIPTION_COLUMN_NAME], unique = true)
    ]
)
data class PointRoomEntity(
    @ColumnInfo(name = RoomConstants.POINTS_COLOR_COLUMN_NAME) val color: Int,
    @ColumnInfo(name = RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME) val planEpochDay: Long,
    @ColumnInfo(name = RoomConstants.POINTS_PLAN_TIME_COLUMN_NAME) val planEpochSecond: Long,
    @ColumnInfo(name = RoomConstants.POINTS_DESCRIPTION_COLUMN_NAME) val description: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "tag") val tag: String,
) {

    fun toPoint(): Point {
        return Point(planEpochSecond, description, color, isDone, tag, planEpochDay)
    }

    companion object {

        fun fromPoint(point: Point): PointRoomEntity {
            return PointRoomEntity(
                color = point.color,
                planEpochDay = point.planEpochDays,
                planEpochSecond = point.planEpochSeconds,
                description = point.description,
                isDone = point.isDone,
                tag = point.tag
            )
        }
    }
}