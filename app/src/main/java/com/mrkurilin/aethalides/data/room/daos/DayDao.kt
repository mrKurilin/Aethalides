package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.*
import com.mrkurilin.aethalides.data.room.entities.DayRoomEntity.Companion.EPOCH_DAY_COLUMN_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Transaction
    @Query(
        "(SELECT ${PointRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${PointRoomEntity.TABLE_NAME})" +
                " UNION " +
                "(SELECT ${EventRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${EventRoomEntity.TABLE_NAME})" +
                " UNION " +
                "(SELECT ${EatenFoodRoomEntity.EPOCH_DAY} as $EPOCH_DAY_COLUMN_NAME FROM ${EatenFoodRoomEntity.TABLE_NAME})" +
                " UNION " +
                "(SELECT ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${SpendingRoomEntity.TABLE_NAME})" +
                " LEFT JOIN " +
                "(SELECT ${EatenFoodRoomEntity.EPOCH_DAY}, SUM(${EatenFoodRoomEntity.KCAL_COUNT}) as caloriesCount FROM ${EatenFoodRoomEntity.TABLE_NAME} GROUP BY ${EatenFoodRoomEntity.EPOCH_DAY}) AS f" +
                " ON $EPOCH_DAY_COLUMN_NAME = f.${EatenFoodRoomEntity.EPOCH_DAY}" +
                " LEFT JOIN " +
                "(SELECT ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} as t, SUM(${SpendingRoomEntity.COST_COLUMN_NAME}) as moneyCount FROM ${SpendingRoomEntity.TABLE_NAME} GROUP BY t) AS s" +
                " ON $EPOCH_DAY_COLUMN_NAME = s.t"
    )
    fun getDayRoomEntitiesToEpochDaysMapFlow(): Flow<List<DayRoomEntity>>
}