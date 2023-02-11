package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Spending
import kotlinx.coroutines.flow.Flow

interface SpendingRepository {

    fun addSpending(spending: Spending): Unit

    fun updateSpending(spending: Spending): Unit

    fun deleteSpending(spending: Spending): Unit

    fun getSpendingFlowByEpochDay(epochDay: Long): Flow<Int>
}