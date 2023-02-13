package com.mrkurilin.aethalides.data.room.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.mrkurilin.aethalides.data.model.Event
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class EventsRoomRepositoryTest {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var eventsRoomRepository: EventsRoomRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AethalidesRoomDatabase::class.java).build()
        eventsRoomRepository = EventsRoomRepository(db.getEventsDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun readEmptyList() = runTest {
        val localDate = LocalDate.now()
        val eventsListFlow = eventsRoomRepository.getEventsListFlowByLocalDate(
            localDate
        )

        eventsListFlow.test {
            assertEquals(listOf<Event>(), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun readAnnualEvent() = runTest {
        val localDate = LocalDate.now()
        var eventsListFlow = eventsRoomRepository.getEventsListFlowByLocalDate(
            localDate
        )

        val event = Event(
            id = 1,
            name = "name",
            localDate = localDate,
            isAnnually = true,
            localTime = LocalTime.now()
        )

        eventsRoomRepository.addEvent(event)

        eventsListFlow.test {
            assertEquals(listOf(event), awaitItem())
        }

        eventsListFlow = eventsRoomRepository.getEventsListFlowByLocalDate(
            localDate.plusYears(1)
        )

        eventsListFlow.test {
            assertEquals(listOf(event), awaitItem())
        }

        eventsListFlow = eventsRoomRepository.getEventsListFlowByLocalDate(
            localDate.plusYears(10)
        )

        eventsListFlow.test {
            assertEquals(listOf(event), awaitItem())
        }
    }
}