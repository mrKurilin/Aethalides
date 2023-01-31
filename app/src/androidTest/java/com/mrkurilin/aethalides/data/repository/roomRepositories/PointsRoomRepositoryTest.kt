package com.mrkurilin.aethalides.data.repository.roomRepositories

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.daos.PointsDao
import com.mrkurilin.aethalides.data.room.repositories.PointsRoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class PointsRoomRepositoryTest {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var points: PointsDao
    private lateinit var pointsRoomRepository: PointsRoomRepository

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
        val firstPoint = Point(
            planEpochDay = LocalDate.now().toEpochDay(),
            description = "Lorem Ipsum",
            color = Color.RED
        )
        val secondPoint = Point(
            planEpochDay = LocalDate.now().toEpochDay() + 1,
            description = "Lorem Ipsum",
            color = Color.RED
        )
        pointsRoomRepository.addPoint(firstPoint)
        pointsRoomRepository.addPoint(secondPoint)

        val pointsFromDb = pointsRoomRepository.getAllPoints()

        assertEquals(listOf(firstPoint, secondPoint), pointsFromDb)
    }

    @Test
    fun readEmptyList() {
        LocalDate.now().toEpochDay()
        val pointsFromDb = pointsRoomRepository.getAllPoints()

        assertEquals(emptyList<Note>(), pointsFromDb)
    }

    @Test
    fun delete() {
        val firstPoint = Point(
            planEpochDay = LocalDate.now().toEpochDay(),
            description = "Lorem Ipsum",
            color = Color.RED
        )
        val secondPoint = Point(
            planEpochDay = LocalDate.now().toEpochDay() + 1,
            description = "Lorem Ipsum",
            color = Color.RED
        )
        pointsRoomRepository.addPoint(firstPoint)
        pointsRoomRepository.addPoint(secondPoint)

        var pointsFromDb = pointsRoomRepository.getAllPoints()

        assertEquals(listOf(firstPoint, secondPoint), pointsFromDb)

        pointsRoomRepository.deletePoint(secondPoint)

        pointsFromDb = pointsRoomRepository.getAllPoints()


        assertEquals(listOf(firstPoint), pointsFromDb)
    }

    @Test
    fun update() {
        val point = Point(
            planEpochDay = LocalDate.now().toEpochDay(),
            description = "Lorem Ipsum",
            color = Color.RED
        )

        pointsRoomRepository.addPoint(point)

        var pointsFromDb = pointsRoomRepository.getAllPoints()

        assertEquals(listOf(point), pointsFromDb)

        val updatedPoint = pointsFromDb.first().copy(tag = "new tag")

        pointsRoomRepository.updatePoint(updatedPoint)

        pointsFromDb = pointsRoomRepository.getAllPoints()

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

        val point = Point(
            planEpochDay = LocalDate.now().toEpochDay(),
            description = "Lorem Ipsum",
            color = Color.RED
        )

        pointsRoomRepository.addPoint(point)

        epochDaysToPointsMapFlow.test {
            assertEquals(mapOf(point.planEpochDay to listOf(point)), awaitItem())
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

        val firstPoint = Point(
            planEpochDay = LocalDate.now().toEpochDay(),
            description = "Lorem Ipsum",
            color = Color.RED
        )

        pointsRoomRepository.addPoint(firstPoint)

        epochDaysToColorsMapFlow.test {
            assertEquals(mapOf(firstPoint.planEpochDay to listOf(firstPoint.color)), awaitItem())
        }
    }
}