package com.mrkurilin.aethalides.data.repository.roomRepositories

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.PointsDao
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class PointsRoomRepositoryTest {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var points: PointsDao
    private lateinit var pointsRoomRepository: PointsRoomRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AethalidesRoomDatabase::class.java).build()
        points = db.getPointsDao()
        pointsRoomRepository = PointsRoomRepository(points)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun writeAndRead() {
        val firstEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val secondEpochSecond: Long = firstEpochSecond + 1
        val epochDay = firstEpochSecond / 60 / 60 / 24
        val firstPoint = Point(
            planEpochSeconds = firstEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )
        val secondPoint = Point(
            planEpochSeconds = secondEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )
        pointsRoomRepository.addPoint(firstPoint)
        pointsRoomRepository.addPoint(secondPoint)

        val pointsFromDb = pointsRoomRepository.getPointsListByDate(epochDay)

        assertEquals(listOf(firstPoint, secondPoint), pointsFromDb)
    }

    @Test
    fun readEmptyList() {
        val epochDay = LocalDate.now().toEpochDay()
        val pointsFromDb = pointsRoomRepository.getPointsListByDate(epochDay)

        assertEquals(emptyList<Note>(), pointsFromDb)
    }

    @Test
    fun delete() {
        val firstEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val secondEpochSecond: Long = firstEpochSecond + 1
        val epochDay = firstEpochSecond / 60 / 60 / 24
        val firstPoint = Point(
            planEpochSeconds = firstEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )
        val secondPoint = Point(
            planEpochSeconds = secondEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )
        pointsRoomRepository.addPoint(firstPoint)
        pointsRoomRepository.addPoint(secondPoint)

        var pointsFromDb = pointsRoomRepository.getPointsListByDate(epochDay)

        assertEquals(listOf(firstPoint, secondPoint), pointsFromDb)

        pointsRoomRepository.deletePoint(secondPoint)

        pointsFromDb = pointsRoomRepository.getPointsListByDate(epochDay)


        assertEquals(listOf(firstPoint), pointsFromDb)
    }

    @Test
    fun update() {
        val firstEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val epochDay = firstEpochSecond / 60 / 60 / 24
        val firstPoint = Point(
            planEpochSeconds = firstEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )

        pointsRoomRepository.addPoint(firstPoint)

        var pointsFromDb = pointsRoomRepository.getPointsListByDate(epochDay)

        assertEquals(listOf(firstPoint), pointsFromDb)

        val updatedPoint = pointsFromDb.first().copy(tag = "new tag")

        pointsRoomRepository.updatePoint(updatedPoint)

        pointsFromDb = pointsRoomRepository.getPointsListByDate(epochDay)

        assertEquals(listOf(updatedPoint), pointsFromDb)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun pointsMapFlow() = runTest {
        val epochDaysToPointsMapFlow = pointsRoomRepository.getEpochDaysToPointsMapFlow()

        epochDaysToPointsMapFlow.test {
            assertEquals(emptyMap<Long, List<Point>>(), awaitItem())
            cancel()
        }

        val firstEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val point = Point(
            planEpochSeconds = firstEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )

        pointsRoomRepository.addPoint(point)

        epochDaysToPointsMapFlow.test {
            assertEquals(mapOf(point.planEpochDays to listOf(point)), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun colorsMapFlow() = runTest {
        val epochDaysToColorsMapFlow = pointsRoomRepository.getEpochDaysToPointsColorsMapFlow()

        epochDaysToColorsMapFlow.test {
            assertEquals(emptyMap<Long, List<Int>>(), awaitItem())
            cancel()
        }

        val firstEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

        val firstPoint = Point(
            planEpochSeconds = firstEpochSecond,
            description = "Lorem Ipsum",
            color = Color.RED
        )

        pointsRoomRepository.addPoint(firstPoint)

        epochDaysToColorsMapFlow.test {
            assertEquals(mapOf(firstPoint.planEpochDays to listOf(firstPoint.color)), awaitItem())
        }
    }
}