package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.EatenFood
import kotlinx.coroutines.flow.Flow

interface EatenFoodRepository {

    fun addEatenFood(eatenFood: EatenFood): Unit

    fun updateEatenFood(eatenFood: EatenFood): Unit

    fun deleteEatenFood(eatenFood: EatenFood): Unit

    fun getCaloriesFlowByEpochDay(toEpochDay: Long): Flow<Int?>
}