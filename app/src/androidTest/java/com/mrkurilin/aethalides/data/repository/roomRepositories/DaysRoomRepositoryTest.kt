package com.mrkurilin.aethalides.data.repository.roomRepositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.mrkurilin.aethalides.data.model.Day
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.repositories.DaysRoomRepository
import com.mrkurilin.aethalides.data.room.repositories.PointsRoomRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class DaysRoomRepositoryTest {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var daysRoomRepository: DaysRoomRepository
    private lateinit var pointsRoomRepository: PointsRoomRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AethalidesRoomDatabase::class.java).build()
        daysRoomRepository = DaysRoomRepository(db.getDayDao())
        pointsRoomRepository = PointsRoomRepository(db.getPointsDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testReadDaysRepository() = runTest {
        val daysToEpochDaysMapFlow = daysRoomRepository.getDaysMapFlow()
        var expectedMap = mapOf<Long, Day>()

        daysToEpochDaysMapFlow.test {
            assertEquals(expectedMap, awaitItem())
        }

        val epochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val epochDay = epochSecond / 60 / 60 / 24

        val point = Point(
            localDateTime = LocalDateTime.now(),
            description = "Test"
        )

        pointsRoomRepository.addPoint(point)
        expectedMap = mapOf(
            epochDay to Day(epochDay = epochDay, points = listOf(point))
        )
        daysToEpochDaysMapFlow.test {
            assertEquals(expectedMap, awaitItem())
        }
    }
}