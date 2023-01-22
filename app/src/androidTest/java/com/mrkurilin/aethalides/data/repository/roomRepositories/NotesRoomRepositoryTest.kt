package com.mrkurilin.aethalides.data.repository.roomRepositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mrkurilin.aethalides.data.model.Note
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.NotesDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class NotesRoomRepositoryTest {

    private lateinit var db: AethalidesRoomDatabase
    private lateinit var notesDao: NotesDao
    private lateinit var notesRoomRepository: NotesRoomRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AethalidesRoomDatabase::class.java).build()
        notesDao = db.getNotesDao()
        notesRoomRepository = NotesRoomRepository(notesDao)
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
        val firstNote = Note(
            epochSecond = firstEpochSecond,
            text = "Lorem Ipsum"
        )
        val secondNote = Note(
            epochSecond = secondEpochSecond,
            text = "Lorem Ipsum"
        )
        notesRoomRepository.addNote(firstNote)
        notesRoomRepository.addNote(secondNote)

        val notesFromDb = notesRoomRepository.getNotesListByDate(epochDay)

        assertEquals(listOf(firstNote, secondNote), notesFromDb)
    }

    @Test
    fun delete() {
        val firstEpochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val secondEpochSecond: Long = firstEpochSecond + 1
        val epochDay = firstEpochSecond / 60 / 60 / 24
        val firstNote = Note(
            epochSecond = firstEpochSecond,
            text = "Lorem Ipsum"
        )
        val noteToDelete = Note(
            epochSecond = secondEpochSecond,
            text = "Lorem Ipsum"
        )

        notesRoomRepository.addNote(noteToDelete)
        notesRoomRepository.addNote(firstNote)

        notesRoomRepository.deleteNote(noteToDelete)

        val notesFromDb = notesRoomRepository.getNotesListByDate(epochDay)

        assertEquals(listOf(firstNote), notesFromDb)
    }

    @Test
    fun update() {
        val epochSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val epochDay = epochSecond / 60 / 60 / 24

        val note = Note(
            epochSecond = epochSecond,
            text = "Lorem Ipsum"
        )

        notesRoomRepository.addNote(note)

        val updatedNote = note.copy(text = "New Text")

        notesRoomRepository.updateNote(updatedNote)

        val notesFromDb = notesRoomRepository.getNotesListByDate(epochDay)

        assertEquals(listOf(updatedNote), notesFromDb)
    }
}