package com.mrkurilin.aethalides.data.room

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AethalidesRoomDatabaseTest : TestCase() {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var pointsDao: PointsDao
    private lateinit var notesDao: NotesDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AethalidesRoomDatabase::class.java).build()
        pointsDao = db.getPointsDao()
        notesDao = db.getNotesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadPoints() {
        val dateForTest: String = "23.01.2022"
        val pointRoomEntity = PointRoomEntity(
            id = 1,
            color = Color.RED,
            planDate = dateForTest,
            planTime = "14:45",
            description = "Description",
            isDone = false,
            tag = ""
        )
        pointsDao.addPoint(pointRoomEntity)
        val pointRoomEntityFromDb = pointsDao.getPointsListByDate(dateForTest)

        assertEquals(listOf(pointRoomEntity), pointRoomEntityFromDb)
    }

    @Test
    fun writeAndReadNotes() {
        val dateForTest: String = "23.01.2022"
        val noteRoomEntity = NoteRoomEntity(
            id = 1,
            date = dateForTest,
            time = "14:45",
            text = "Lorem Ipsum"
        )
        notesDao.addNote(noteRoomEntity)
        val noteEntitiesListFromDb = notesDao.getNotesListByDate(dateForTest)

        assertEquals(listOf(noteRoomEntity), noteEntitiesListFromDb)
    }
}