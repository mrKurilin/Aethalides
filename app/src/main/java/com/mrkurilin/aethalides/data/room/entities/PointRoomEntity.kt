package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.DESCRIPTION_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.EPOCH_DAY_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.PLAN_TIME_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [
        PLAN_TIME_COLUMN_NAME,
        DESCRIPTION_COLUMN_NAME,
    ],
    indices = [
        Index(
            value = [
                EPOCH_DAY_COLUMN_NAME,
            ]
        )
    ]
)
data class PointRoomEntity(
    @ColumnInfo(name = COLOR_COLUMN_NAME) val color: Int? = 0,
    @ColumnInfo(name = EPOCH_DAY_COLUMN_NAME) val planEpochDay: Long? = 0,
    @ColumnInfo(name = PLAN_TIME_COLUMN_NAME) val planEpochSecond: Long = 0,
    @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) val description: String = "test",
    @ColumnInfo(name = IS_DONE_COLUMN_NAME) val isDone: Boolean? = false,
    @ColumnInfo(name = TAG_COLUMN_NAME) val tag: String? = "",
) {

    fun toPoint(): Point {
        return Point(
            planEpochDay = planEpochDay!!,
            planEpochSecond = planEpochSecond,
            description = description,
            color = color!!,
            isDone = isDone!!,
            tag = tag!!,
        )
    }

    companion object {

        const val TABLE_NAME = "points"
        const val COLOR_COLUMN_NAME = "color"
        const val EPOCH_DAY_COLUMN_NAME = "point_epoch_day"
        const val PLAN_TIME_COLUMN_NAME = "plan_time"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val TAG_COLUMN_NAME = "tag"
        const val IS_DONE_COLUMN_NAME = "is_done"

        fun fromPoint(point: Point): PointRoomEntity {
            return PointRoomEntity(
                color = point.color,
                planEpochDay = point.planEpochDay,
                planEpochSecond = point.planEpochSecond,
                description = point.description,
                isDone = point.isDone,
                tag = point.tag
            )
        }
    }
}