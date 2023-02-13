package com.mrkurilin.aethalides.data.room.repositories

import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.repository.EventsRepository
import com.mrkurilin.aethalides.data.room.daos.EventsDao
import com.mrkurilin.aethalides.data.room.entities.EventRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

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

    override fun getEventsListFlowByLocalDate(
        localDate: LocalDate
    ): Flow<List<Event>> {
        val eventsListFlowByDate = dao.getEventsListFlowByDate(
            year = localDate.year,
            month = localDate.monthValue,
            dayOfMonth = localDate.dayOfMonth
        )
        return eventsListFlowByDate.map { eventRoomEntities ->
            eventRoomEntities.map { eventRoomEntity ->
                eventRoomEntity.toEvent()
            }
        }
    }
}