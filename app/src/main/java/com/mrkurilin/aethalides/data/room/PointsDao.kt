package com.mrkurilin.aethalides.data.room

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity

@Dao
interface PointsDao {

    @Query("SELECT * FROM ${RoomConstants.POINTS_TABLE_NAME} WHERE ${RoomConstants.POINTS_PLAN_DATE_COLUMN_NAME} = :epochDay")
    fun getPointsListByDate(epochDay: Long): List<PointRoomEntity>

    @Insert
    fun addPoint(point: PointRoomEntity): Unit

    @Delete
    fun deletePoint(point: PointRoomEntity): Unit

    @Query("DELETE FROM ${RoomConstants.POINTS_TABLE_NAME} WHERE ${RoomConstants.POINTS_TAG_COLUMN_NAME} = :tag")
    fun deletePointsByTag(tag: String): Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updatePoint(point: PointRoomEntity): Unit
}