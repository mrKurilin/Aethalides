package com.mrkurilin.aethalides.data.room.repositories

import com.mrkurilin.aethalides.data.model.Spending
import com.mrkurilin.aethalides.data.repository.SpendingRepository
import com.mrkurilin.aethalides.data.room.daos.SpendingDao
import com.mrkurilin.aethalides.data.room.entities.SpendingRoomEntity
import kotlinx.coroutines.flow.Flow

class SpendingRoomRepository(
    private val dao: SpendingDao
) : SpendingRepository {

    override fun addSpending(spending: Spending) {
        dao.addSpendingRoomEntity(SpendingRoomEntity.fromSpending(spending))
    }

    override fun updateSpending(spending: Spending) {
        dao.updateSpendingRoomEntity(SpendingRoomEntity.fromSpending(spending))
    }

    override fun deleteSpending(spending: Spending) {
        dao.deleteSpendingRoomEntity(SpendingRoomEntity.fromSpending(spending))
    }

    override fun getSpendingFlowByEpochDay(epochDay: Long): Flow<Int?> {
        return dao.getSpendingFlowByEpochDay(epochDay)
    }
}