package com.mrkurilin.aethalides.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrkurilin.aethalides.data.model.Spending
import com.mrkurilin.aethalides.data.room.entities.SpendingRoomEntity.Companion.TABLE_NAME
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(
    tableName = TABLE_NAME,
)
data class SpendingRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = NAME_COLUMN_NAME) val name: String,
    @ColumnInfo(name = COST_COLUMN_NAME) val cost: Float,
    @ColumnInfo(name = EPOCH_SECOND_COLUMN_NAME) val utcEpochSecond: Long = LocalDateTime.now()
        .toEpochSecond(ZoneOffset.UTC),
    @ColumnInfo(name = EPOCH_DAY_COLUMN_NAME) val epochDay: Long = utcEpochSecond / 60 / 60 / 24
) {

    fun toSpending(): Spending {
        return Spending(
            name = name,
            cost = cost,
            utcEpochSecond = utcEpochSecond,
            epochDay = epochDay,
        )
    }

    companion object {

        const val TABLE_NAME = "spending"
        const val NAME_COLUMN_NAME = "name"
        const val COST_COLUMN_NAME = "cost"
        const val EPOCH_SECOND_COLUMN_NAME = "utc_epoch_second"
        const val EPOCH_DAY_COLUMN_NAME = "spending_epoch_day"

        fun fromSpending(spending: Spending): SpendingRoomEntity {
            return SpendingRoomEntity(
                name = spending.name,
                cost = spending.cost,
                utcEpochSecond = spending.utcEpochSecond,
                epochDay = spending.epochDay,
            )
        }
    }
}