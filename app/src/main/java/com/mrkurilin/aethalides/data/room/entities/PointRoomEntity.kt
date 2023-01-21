package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.RoomConstants

@Entity(
    tableName = RoomConstants.POINTS_TABLE_NAME
)
data class PointRoomEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "color") val color: Int,
    @ColumnInfo(name = "plan_date") val planDate: String,
    @ColumnInfo(name = "plan_time") val planTime: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "tag") val tag: String,
) {

    fun toPoint(): Point {
        return Point(color, planDate, planTime, description, isDone, tag)
    }

    companion object {

        fun fromPoint(point: Point): PointRoomEntity {
            return PointRoomEntity(
                id = 0,
                color = point.color,
                planDate = point.planDate,
                planTime = point.planTime,
                description = point.description,
                isDone = point.isDone,
                tag = point.tag
            )
        }
    }
}