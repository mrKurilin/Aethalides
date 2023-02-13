package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EatenFoodDao {

    @Insert
    fun addEatenFood(eatenFoodRoomEntity: EatenFoodRoomEntity): Unit

    @Query(
        "SELECT * " +
                "FROM ${EatenFoodRoomEntity.TABLE_NAME} " +
                "WHERE ${EatenFoodRoomEntity.EPOCH_DAY} = :epochDay"
    )
    fun getEatenFoodListFlowByEpochDay(epochDay: Long): Flow<List<EatenFoodRoomEntity>>

    @Query(
        "SELECT SUM(${EatenFoodRoomEntity.KCAL_COUNT}) " +
                "FROM ${EatenFoodRoomEntity.TABLE_NAME} " +
                "WHERE ${EatenFoodRoomEntity.EPOCH_DAY} = :epochDay"
    )
    fun getEatenFoodCaloriesFlowByEpochDay(epochDay: Long): Flow<Int>

    @Update
    fun updateEatenFood(eatenFoodRoomEntity: EatenFoodRoomEntity): Unit

    @Delete
    fun deleteEatenFood(eatenFoodRoomEntity: EatenFoodRoomEntity): Unit

    @Query(
        "SELECT SUM(${EatenFoodRoomEntity.KCAL_COUNT}) " +
                "FROM ${EatenFoodRoomEntity.TABLE_NAME} " +
                "WHERE ${EatenFoodRoomEntity.EPOCH_DAY} = :epochDay"
    )
    fun getCaloriesFlowByEpochDay(epochDay: Long): Flow<Int?>
}