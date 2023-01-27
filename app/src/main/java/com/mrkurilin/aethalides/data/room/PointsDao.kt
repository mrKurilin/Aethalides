package com.mrkurilin.aethalides.data.room

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointsDao {

    @Query("SELECT * FROM ${RoomConstants.POINTS_TABLE_NAME} WHERE ${RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME} = :epochDay")
    fun getPointsListByDate(epochDay: Long): List<PointRoomEntity>

    @Insert
    fun addPoint(point: PointRoomEntity): Unit

    @Delete
    fun deletePoint(point: PointRoomEntity): Unit

    @Query("DELETE FROM ${RoomConstants.POINTS_TABLE_NAME} WHERE ${RoomConstants.POINTS_TAG_COLUMN_NAME} = :tag")
    fun deletePointsByTag(tag: String): Unit

    @Update
    fun updatePoint(point: PointRoomEntity): Unit

    @Query("SELECT DISTINCT ${RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME} FROM ${RoomConstants.POINTS_TABLE_NAME}")
    fun getAllPlanDatesFromDb(): List<Long>

    @Query("SELECT DISTINCT ${RoomConstants.POINTS_COLOR_COLUMN_NAME} FROM ${RoomConstants.POINTS_TABLE_NAME} WHERE ${RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME} = :epochDay")
    fun getAllPointsColorsByEpochDay(epochDay: Long): List<Int>

    @Query("SELECT * FROM ${RoomConstants.POINTS_TABLE_NAME}")
    fun getAllPointRoomEntitiesFlow(): Flow<List<PointRoomEntity>>

    @Query("SELECT * FROM ${RoomConstants.POINTS_TABLE_NAME} where ${RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME} = :epochDay")
    fun getAllPointsByEpochDay(epochDay: Long): List<PointRoomEntity>

    @MapInfo(keyColumn = RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME)
    @Query("SELECT * FROM ${RoomConstants.POINTS_TABLE_NAME} ORDER BY ${RoomConstants.POINTS_PLAN_TIME_COLUMN_NAME} ASC")
    fun getEpochDaysToPointsRoomEntitiesMapFlow(): Flow<Map<Long, List<PointRoomEntity>>>

    @MapInfo(keyColumn = RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME, valueColumn = RoomConstants.POINTS_COLOR_COLUMN_NAME)
    @Query("SELECT DISTINCT ${RoomConstants.POINTS_COLOR_COLUMN_NAME}, ${RoomConstants.POINTS_PLAN_EPOCH_DAY_COLUMN_NAME} FROM ${RoomConstants.POINTS_TABLE_NAME} ORDER BY ${RoomConstants.POINTS_PLAN_TIME_COLUMN_NAME} ASC")
    fun getEpochDaysToColorsMapFlow(): Flow<Map<Long, List<Int>>>
}