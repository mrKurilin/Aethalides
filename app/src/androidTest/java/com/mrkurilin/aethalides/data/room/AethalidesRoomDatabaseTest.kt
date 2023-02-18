package com.mrkurilin.aethalides.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mrkurilin.aethalides.data.room.daos.NotesDao
import com.mrkurilin.aethalides.data.room.daos.PointsDao
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset

@RunWith(AndroidJUnit4::class)
class AethalidesRoomDatabaseTest {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var pointsDao: PointsDao
    private lateinit var notesDao: NotesDao

    @Before
    fun setUp() {
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
    fun writeAndReadNotes() {
        val epochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val epochDay = epochSecond / 60 / 60 / 24
        val noteRoomEntity = NoteRoomEntity(
            epochDay = epochDay,
            epochSecond = epochSecond,
            text = "Lorem Ipsum"
        )
        notesDao.addNote(noteRoomEntity)
        val noteEntitiesListFromDb = notesDao.getNotesListFlowByEpochDay(epochDay)

        assertEquals(listOf(noteRoomEntity), noteEntitiesListFromDb)
    }
}