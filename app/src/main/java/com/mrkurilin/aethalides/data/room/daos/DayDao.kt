package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.RoomConstants
import com.mrkurilin.aethalides.data.room.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Transaction
    @MapInfo(keyColumn = RoomConstants.EPOCH_DAY)
    @Query(
        "SELECT * FROM (" +
                "SELECT ${EatenFoodRoomEntity.EPOCH_DAY} as ${RoomConstants.EPOCH_DAY} FROM ${EatenFoodRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${EventRoomEntity.EPOCH_DAY} as ${RoomConstants.EPOCH_DAY} FROM ${EventRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${NoteRoomEntity.EPOCH_DAY} as ${RoomConstants.EPOCH_DAY} FROM ${NoteRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${PointRoomEntity.EPOCH_DAY} as ${RoomConstants.EPOCH_DAY} FROM ${PointRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} as ${RoomConstants.EPOCH_DAY} FROM ${SpendingRoomEntity.TABLE_NAME})" +
                " LEFT JOIN " +
                "(SELECT ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME}, SUM(${SpendingRoomEntity.COST_COLUMN_NAME}) as moneyCount FROM ${SpendingRoomEntity.TABLE_NAME} GROUP BY ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME})" +
                " ON ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} = ${RoomConstants.EPOCH_DAY}" +
                " LEFT JOIN " +
                "(SELECT ${EatenFoodRoomEntity.EPOCH_DAY}, SUM(${EatenFoodRoomEntity.KCAL_COUNT}) as caloriesCount FROM ${EatenFoodRoomEntity.TABLE_NAME} GROUP BY ${EatenFoodRoomEntity.EPOCH_DAY})" +
                " ON ${EatenFoodRoomEntity.EPOCH_DAY} = ${RoomConstants.EPOCH_DAY}" +
                " LEFT JOIN ${PointRoomEntity.TABLE_NAME} ON ${PointRoomEntity.EPOCH_DAY} = ${RoomConstants.EPOCH_DAY}" +
                " LEFT JOIN ${EventRoomEntity.TABLE_NAME} ON ${EventRoomEntity.EPOCH_DAY} = ${RoomConstants.EPOCH_DAY}"
    )
    fun getDayRoomEntitiesListFlow(): Flow<Map<Long, DayRoomEntity>>
}