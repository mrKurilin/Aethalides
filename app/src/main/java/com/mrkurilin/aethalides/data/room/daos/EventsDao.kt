package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {

    @Insert
    fun addEvent(eventRoomEntity: EventRoomEntity): Unit

    @Query("SELECT * FROM ${EventRoomEntity.TABLE_NAME} " +
            "WHERE ${EventRoomEntity.MONTH_COLUMN_NAME} = :month " +
            "AND ${EventRoomEntity.DAY_COLUMN_NAME} = :day " +
            "AND (${EventRoomEntity.YEAR_COLUMN_NAME} = :year " +
            "OR ${EventRoomEntity.IS_EVERY_YEAR_COLUMN_NAME} = true)")
    fun getEventsListFlowByDate(
        year: Int,
        month: Int,
        day: Int,
    ): Flow<List<EventRoomEntity>>

    @Update
    fun updateEvent(eventRoomEntity: EventRoomEntity): Unit

    @Delete
    fun deleteEvent(eventRoomEntity: EventRoomEntity): Unit
}