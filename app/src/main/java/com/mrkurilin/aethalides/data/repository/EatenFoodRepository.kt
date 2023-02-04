package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.EatenFood

interface EatenFoodRepository {

    fun addEatenFood(eatenFood: EatenFood): Unit

    fun updateEatenFood(eatenFood: EatenFood): Unit

    fun deleteEatenFood(eatenFood: EatenFood): Unit
}