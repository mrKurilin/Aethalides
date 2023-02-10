package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.DESCRIPTION_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.EPOCH_DAY
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.PLAN_TIME_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [
        PLAN_TIME_COLUMN_NAME,
        DESCRIPTION_COLUMN_NAME,
    ],
    indices = [Index(value = [EPOCH_DAY])]
)
data class PointRoomEntity(
    @ColumnInfo(name = EPOCH_DAY) val planEpochDay: Long,
    @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) val description: String,
    @ColumnInfo(name = COLOR_COLUMN_NAME) val color: Int,
    @ColumnInfo(name = IS_DONE_COLUMN_NAME) val isDone: Boolean,
    @ColumnInfo(name = TAG_COLUMN_NAME) val tag: String,
    @ColumnInfo(name = NEED_TO_REMIND_COLUMN_NAME) val needToRemind: Boolean,
    @ColumnInfo(name = PLAN_TIME_COLUMN_NAME) val planEpochSecond: Long,
    @ColumnInfo(name = REMIND_TIME_COLUMN_NAME) val remindTime: Long,
    @ColumnInfo(name = DAY_OF_WEEK_COLUMN_NAME) val dayOfWeek: Int,
    @ColumnInfo(name = DAY_OF_MONTH_COLUMN_NAME) val dayOfMonth: Int,
    @ColumnInfo(name = MONTH_COLUMN_NAME) val month: Int,
    @ColumnInfo(name = YEAR_COLUMN_NAME) val year: Int,
    @ColumnInfo(name = IS_DAILY_COLUMN_NAME) val isDaily: Boolean,
    @ColumnInfo(name = IS_WEEKLY_COLUMN_NAME) val isWeekly: Boolean,
    @ColumnInfo(name = IS_MONTHLY_COLUMN_NAME) val isMonthly: Boolean,
    @ColumnInfo(name = IS_ANNUALLY_COLUMN_NAME) val isAnnually: Boolean,
    @ColumnInfo(name = TRACK_IN_HISTORY_COLUMN_NAME) val trackInHistory: Boolean,
) {

    fun toPoint(): Point {
        return Point(
            planEpochDay = planEpochDay,
            description = description,
            color = color,
            isDone = isDone,
            tag = tag,
            needToRemind = needToRemind,
            planEpochSecond = planEpochSecond,
            remindTime = remindTime,
            dayOfWeek = dayOfWeek,
            dayOfMonth = dayOfMonth,
            month = month,
            year = year,
            isDaily = isDaily,
            isWeekly = isWeekly,
            isMonthly = isMonthly,
            isAnnually = isAnnually,
            trackInHistory = trackInHistory,
        )
    }

    companion object {

        const val TABLE_NAME = "points"
        const val EPOCH_DAY = "point_epoch_day"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val COLOR_COLUMN_NAME = "color"
        const val IS_DONE_COLUMN_NAME = "is_done"
        const val PLAN_TIME_COLUMN_NAME = "plan_time"
        const val TAG_COLUMN_NAME = "tag"
        const val NEED_TO_REMIND_COLUMN_NAME = "need_to_remind"
        const val REMIND_TIME_COLUMN_NAME = "remind_time"
        const val DAY_OF_WEEK_COLUMN_NAME = "day_of_week"
        const val DAY_OF_MONTH_COLUMN_NAME = "day_of_month"
        const val MONTH_COLUMN_NAME = "month"
        const val YEAR_COLUMN_NAME = "year"
        const val IS_DAILY_COLUMN_NAME = "is_daily"
        const val IS_WEEKLY_COLUMN_NAME = "is_weekly"
        const val IS_MONTHLY_COLUMN_NAME = "is_monthly"
        const val IS_ANNUALLY_COLUMN_NAME = "is_annually"
        const val TRACK_IN_HISTORY_COLUMN_NAME = "track_in_history"

        fun fromPoint(point: Point): PointRoomEntity {
            return PointRoomEntity(
                planEpochDay = point.planEpochDay,
                description = point.description,
                color = point.color,
                isDone = point.isDone,
                tag = point.tag,
                needToRemind = point.needToRemind,
                planEpochSecond = point.planEpochSecond,
                remindTime = point.remindTime,
                dayOfWeek = point.dayOfWeek,
                dayOfMonth = point.dayOfMonth,
                month = point.month,
                year = point.year,
                isDaily = point.isDaily,
                isWeekly = point.isWeekly,
                isMonthly = point.isMonthly,
                isAnnually = point.isAnnually,
                trackInHistory = point.trackInHistory,
            )
        }
    }
}