package com.mrkurilin.aethalides.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZoneOffset

@Parcelize
data class Note(
    val epochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val text: String,
    val epochDay: Long = epochSecond / 60 / 60 / 24
) : Parcelable