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
}