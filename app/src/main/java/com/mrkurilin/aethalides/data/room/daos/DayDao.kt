package com.mrkurilin.aethalides.data.room.daos

import androidx.room.*
import com.mrkurilin.aethalides.data.room.entities.*
import com.mrkurilin.aethalides.data.room.entities.DayRoomEntity.Companion.EPOCH_DAY_COLUMN_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Transaction
    @Query(
        "SELECT ${PointRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${PointRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${EventRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${EventRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${EatenFoodRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${EatenFoodRoomEntity.TABLE_NAME}" +
                " UNION " +
                "SELECT ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} as $EPOCH_DAY_COLUMN_NAME FROM ${SpendingRoomEntity.TABLE_NAME}" +
                " LEFT JOIN " +
                "(SELECT ${EatenFoodRoomEntity.EPOCH_DAY_COLUMN_NAME}, SUM(${EatenFoodRoomEntity.KCAL_COUNT_COLUMN_NAME}) as caloriesCount FROM ${EatenFoodRoomEntity.TABLE_NAME} GROUP BY ${EatenFoodRoomEntity.EPOCH_DAY_COLUMN_NAME})" +
                " ON ${EatenFoodRoomEntity.EPOCH_DAY_COLUMN_NAME} = $EPOCH_DAY_COLUMN_NAME" +
                " LEFT JOIN " +
                "(SELECT ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME} as new_epoch_day, SUM(${SpendingRoomEntity.COST_COLUMN_NAME}) as moneyCount FROM ${SpendingRoomEntity.TABLE_NAME} GROUP BY ${SpendingRoomEntity.EPOCH_DAY_COLUMN_NAME})" +
                " ON (new_epoch_day = $EPOCH_DAY_COLUMN_NAME)"
    )
    fun getDayRoomEntitiesToEpochDaysMapFlow(): Flow<List<DayRoomEntity>>

    @Insert
    fun addPoint(pointRoomEntity: PointRoomEntity)

    @Insert
    fun addNote(noteRoomEntity: NoteRoomEntity)
}