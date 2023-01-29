package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.SpendingRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendingDao {

    @Insert
    fun addSpending(spendingRoomEntity: SpendingRoomEntity): Unit

    @Query(
        "SELECT * FROM ${SpendingRoomEntity.TABLE_NAME}" +
                "WHERE ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} = :epochDay"
    )
    fun getSpendingListFlowByEpochDay(epochDay: Long): Flow<List<SpendingRoomEntity>>

    @Query(
        "SELECT SUM(${SpendingRoomEntity.COST_COLUMN_NAME})" +
                "FROM ${SpendingRoomEntity.TABLE_NAME}" +
                "WHERE ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} = :epochDay"
    )
    fun getSpendingCostFlowByEpochDay(epochDay: Long): Flow<Int>

    @Update
    fun updateSpending(spendingRoomEntity: SpendingRoomEntity): Unit

    @Delete
    fun deleteSpending(spendingRoomEntity: SpendingRoomEntity)
}