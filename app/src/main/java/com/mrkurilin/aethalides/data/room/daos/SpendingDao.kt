package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.SpendingRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendingDao {

    @Insert
    fun addSpendingRoomEntity(spendingRoomEntity: SpendingRoomEntity): Unit

    @Update
    fun updateSpendingRoomEntity(spendingRoomEntity: SpendingRoomEntity): Unit

    @Delete
    fun deleteSpendingRoomEntity(spendingRoomEntity: SpendingRoomEntity): Unit

    @Query(
        "SELECT SUM(${SpendingRoomEntity.COST_COLUMN_NAME}) " +
                "FROM ${SpendingRoomEntity.TABLE_NAME} " +
                "WHERE ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} = :epochDay"
    )
    fun getSpendingFlowByEpochDay(epochDay: Long): Flow<Int>
}