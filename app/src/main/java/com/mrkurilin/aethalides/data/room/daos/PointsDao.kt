package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointsDao {

    @Insert
    fun addPoint(point: PointRoomEntity): Unit

    @Query("SELECT * FROM ${PointRoomEntity.TABLE_NAME}")
    fun getAllPointRoomEntities(): List<PointRoomEntity>

    @MapInfo(keyColumn = PointRoomEntity.EPOCH_DAY)
    @Query(
        "SELECT * FROM ${PointRoomEntity.TABLE_NAME} " +
                "ORDER BY ${PointRoomEntity.PLAN_TIME_COLUMN_NAME} ASC"
    )
    fun getEpochDaysToPointsRoomEntitiesMapFlow(): Flow<Map<Long, List<PointRoomEntity>>>

    @MapInfo(
        keyColumn = PointRoomEntity.EPOCH_DAY,
        valueColumn = PointRoomEntity.COLOR_COLUMN_NAME
    )
    @Query(
        "SELECT DISTINCT ${PointRoomEntity.COLOR_COLUMN_NAME}, " +
                "${PointRoomEntity.EPOCH_DAY} FROM ${PointRoomEntity.TABLE_NAME} " +
                "ORDER BY ${PointRoomEntity.PLAN_TIME_COLUMN_NAME} ASC"
    )
    fun getEpochDaysToColorsMapFlow(): Flow<Map<Long, List<Int>>>

    @Update
    fun updatePoint(point: PointRoomEntity): Unit

    @Delete
    fun deletePoint(point: PointRoomEntity): Unit

    @Query(
        "DELETE FROM ${PointRoomEntity.TABLE_NAME} " +
                "WHERE ${PointRoomEntity.TAG_COLUMN_NAME} = :tag"
    )
    fun deletePointsByTag(tag: String): Unit

    @Query(
        "SELECT * FROM ${PointRoomEntity.TABLE_NAME} " +
                "WHERE " +
                "( " +
                    "${PointRoomEntity.IS_DAILY_COLUMN_NAME} = true " +
                ") " +
                "OR " +
                "( " +
                    "${PointRoomEntity.DAY_OF_WEEK_COLUMN_NAME} = :dayOfWeek " +
                    "AND " +
                    "${PointRoomEntity.IS_WEEKLY_COLUMN_NAME} = true " +
                ") " +
                "OR " +
                "( " +
                    "${PointRoomEntity.DAY_OF_MONTH_COLUMN_NAME} = :dayOfMonth " +
                    "AND " +
                    "${PointRoomEntity.IS_MONTHLY_COLUMN_NAME} = true" +
                ") " +
                "OR " +
                "( " +
                    "${PointRoomEntity.DAY_OF_MONTH_COLUMN_NAME} = :dayOfMonth " +
                    "AND " +
                    "${PointRoomEntity.MONTH_COLUMN_NAME} = :month " +
                    "AND " +
                    "( " +
                        "${PointRoomEntity.YEAR_COLUMN_NAME} = :year " +
                        "OR " +
                        "${PointRoomEntity.IS_ANNUALLY_COLUMN_NAME} = true " +
                    ") " +
                ") " +
                "ORDER BY ${PointRoomEntity.IS_DONE_COLUMN_NAME}"

    )
    fun getPointRoomEntitiesListFlowByDate(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        dayOfWeek: Int,
    ): Flow<List<PointRoomEntity>>
}