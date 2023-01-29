package com.mrkurilin.aethalides.data.model

import androidx.room.ColumnInfo

data class Day(
    @ColumnInfo(name = "epoch_day")
    val epochDay: Long,
)