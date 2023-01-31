package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Event

interface EventsRepository {

    fun addEvent(event: Event): Unit

    fun updateEvent(event: Event): Unit

    fun deleteEvent(event: Event): Unit
}