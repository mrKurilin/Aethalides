package com.mrkurilin.aethalides.data.room.repositories

import com.mrkurilin.aethalides.data.model.EatenFood
import com.mrkurilin.aethalides.data.repository.EatenFoodRepository
import com.mrkurilin.aethalides.data.room.daos.EatenFoodDao
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity
import kotlinx.coroutines.flow.Flow

class EatenFoodRoomRepository(private val eatenFoodDao: EatenFoodDao) : EatenFoodRepository {

    override fun addEatenFood(eatenFood: EatenFood) {
        eatenFoodDao.addEatenFood(EatenFoodRoomEntity.fromEatenFood(eatenFood))
    }

    override fun updateEatenFood(eatenFood: EatenFood) {
        eatenFoodDao.updateEatenFood(EatenFoodRoomEntity.fromEatenFood(eatenFood))
    }

    override fun deleteEatenFood(eatenFood: EatenFood) {
        eatenFoodDao.deleteEatenFood(EatenFoodRoomEntity.fromEatenFood(eatenFood))
    }

    override fun getCaloriesFlowByEpochDay(toEpochDay: Long): Flow<Int?> {
        return eatenFoodDao.getCaloriesFlowByEpochDay(toEpochDay)
    }
}