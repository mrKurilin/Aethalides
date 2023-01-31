package com.mrkurilin.aethalides.data.room.repositories

import com.mrkurilin.aethalides.data.model.Day
import com.mrkurilin.aethalides.data.repository.DaysRepository
import com.mrkurilin.aethalides.data.room.daos.DayDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DaysRoomRepository(private val dayDao: DayDao) : DaysRepository {

    override fun getDaysToEpochDaysMapFlow(): Flow<Map<Long, Day>> {
        return dayDao.getDayRoomEntitiesToEpochDaysMapFlow().map { mapDayRoomEntity ->
            mapDayRoomEntity.mapValues { entry ->
                entry.value.toDay()
            }
        }
    }
}