package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.mrkurilin.aethalides.data.model.EatenFood
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity.Companion.NAME_COLUMN_NAME
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity.Companion.TABLE_NAME
import com.mrkurilin.aethalides.data.room.entities.EatenFoodRoomEntity.Companion.UTC_EPOCH_SECOND_COLUMN_NAME
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [
        UTC_EPOCH_SECOND_COLUMN_NAME,
        NAME_COLUMN_NAME
    ]
)
data class EatenFoodRoomEntity(
    @ColumnInfo(name = NAME_COLUMN_NAME) val name: String,
    @ColumnInfo(name = KCAL_COUNT_COLUMN_NAME) val kCalCount: Int,
    @ColumnInfo(name = UTC_EPOCH_SECOND_COLUMN_NAME) val utcEpochSecond: Long = LocalDateTime.now()
        .toEpochSecond(ZoneOffset.UTC),
    @ColumnInfo(name = EPOCH_DAY_COLUMN_NAME) val epochDay: Long = utcEpochSecond / 60 / 60 / 24
) {

    fun toEatenFood(): EatenFood {
        return EatenFood(
            name = name,
            kCalCount = kCalCount,
            utcEpochSecond = utcEpochSecond,
            epochDay = epochDay,
        )
    }

    companion object {

        const val TABLE_NAME = "eaten_food"
        const val NAME_COLUMN_NAME = "name"
        const val KCAL_COUNT_COLUMN_NAME = "kcal_count"
        const val UTC_EPOCH_SECOND_COLUMN_NAME = "UTC_epoch_second"
        const val EPOCH_DAY_COLUMN_NAME = "eaten_food_epoch_day"

        fun fromEatenFood(eatenFood: EatenFood): EatenFoodRoomEntity {
            return EatenFoodRoomEntity(
                name = eatenFood.name,
                kCalCount = eatenFood.kCalCount,
                utcEpochSecond = eatenFood.utcEpochSecond,
                epochDay = eatenFood.epochDay,
            )
        }
    }
}