package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.mrkurilin.aethalides.data.model.EatenFood
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity.Companion.NAME
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity.Companion.TABLE_NAME
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity.Companion.UTC_EPOCH_SECOND

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [
        UTC_EPOCH_SECOND,
        NAME
    ]
)
data class EatenFoodRoomEntity(
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = KCAL_COUNT) val kCalCount: Int,
    @ColumnInfo(name = EATEN_FOOD_MEASURE) val eatenFoodMeasure: String,
    @ColumnInfo(name = EATEN_FOOD_COUNT) val eatenFoodCount: Int,
    @ColumnInfo(name = UTC_EPOCH_SECOND) val utcEpochSecond: Long,
    @ColumnInfo(name = EPOCH_DAY) val epochDay: Long,
) {

    fun toEatenFood(): EatenFood {
        return EatenFood(
            name = name,
            kCalCount = kCalCount,
            eatenFoodMeasure = eatenFoodMeasure,
            eatenFoodCount = eatenFoodCount,
            utcEpochSecond = utcEpochSecond,
            epochDay = epochDay,
        )
    }

    companion object {

        const val TABLE_NAME = "eaten_food"
        const val NAME = "name"
        const val KCAL_COUNT = "kcal_count"
        const val UTC_EPOCH_SECOND = "UTC_epoch_second"
        const val EPOCH_DAY = "eaten_food_epoch_day"
        const val EATEN_FOOD_MEASURE = "eaten_food_measure"
        const val EATEN_FOOD_COUNT = "eaten_food_count"

        fun fromEatenFood(eatenFood: EatenFood): EatenFoodRoomEntity {
            return EatenFoodRoomEntity(
                name = eatenFood.name,
                kCalCount = eatenFood.kCalCount,
                eatenFoodMeasure = eatenFood.eatenFoodMeasure,
                eatenFoodCount = eatenFood.eatenFoodCount,
                utcEpochSecond = eatenFood.utcEpochSecond,
                epochDay = eatenFood.epochDay,
            )
        }
    }
}