package com.mrkurilin.aethalides.data.util

sealed class EntryState {

    object Add : EntryState()

    object Edit : EntryState()
}