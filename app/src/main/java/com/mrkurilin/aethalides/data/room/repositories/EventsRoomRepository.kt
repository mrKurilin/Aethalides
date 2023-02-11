package com.mrkurilin.aethalides.data.room.repositories

import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.repository.EventsRepository
import com.mrkurilin.aethalides.data.room.daos.EventsDao
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getEventsListFlowByEpochDay(epochDay: Long): Flow<List<Event>> {
        return dao.getEventRoomEntitiesListFlowByEpochDay(epochDay).map { eventRoomEntites ->
            eventRoomEntites.map { eventRoomEntity ->
                eventRoomEntity.toEvent()
            }
        }
    }
}