package com.mrkurilin.aethalides.data.model

data class Note(
    val epochSecond: Long,
    val text: String,
    val epochDay: Long = epochSecond / 60 / 60 / 24
)