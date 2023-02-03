package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Day
import kotlinx.coroutines.flow.Flow

interface DaysRepository {

    fun getDaysToEpochDaysMapFlow(): Flow<List<Day>>
}