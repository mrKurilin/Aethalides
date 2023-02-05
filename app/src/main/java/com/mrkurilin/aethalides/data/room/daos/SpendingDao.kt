package com.mrkurilin.aethalides.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.mrkurilin.aethalides.data.room.entities.SpendingRoomEntity

@Dao
interface SpendingDao {

    @Insert
    fun addSpendingRoomEntity(spendingRoomEntity: SpendingRoomEntity): Unit

    @Update
    fun updateSpendingRoomEntity(spendingRoomEntity: SpendingRoomEntity): Unit

    @Delete
    fun deleteSpendingRoomEntity(spendingRoomEntity: SpendingRoomEntity): Unit
}