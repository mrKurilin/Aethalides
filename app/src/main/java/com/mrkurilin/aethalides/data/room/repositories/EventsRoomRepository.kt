package com.mrkurilin.aethalides.data.room.repositories

import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.repository.EventsRepository
import com.mrkurilin.aethalides.data.room.daos.EventsDao
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity

class EventsRoomRepository(private val dao: EventsDao) : EventsRepository {

    override fun addEvent(event: Event) {
        dao.addEvent(EventRoomEntity.fromEvent(event))
    }

    override fun updateEvent(event: Event) {
        dao.updateEvent(EventRoomEntity.fromEvent(event))
    }

    override fun deleteEvent(event: Event) {
        dao.deleteEvent(EventRoomEntity.fromEvent(event))
    }
}