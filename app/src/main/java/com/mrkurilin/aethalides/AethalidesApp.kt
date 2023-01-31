package com.mrkurilin.aethalides

import android.app.Application
import androidx.navigation.NavController
import androidx.room.Room
import com.mrkurilin.aethalides.data.repository.DaysRepository
import com.mrkurilin.aethalides.data.repository.EventsRepository
import com.mrkurilin.aethalides.data.repository.NotesRepository
import com.mrkurilin.aethalides.data.repository.PointsRepository
import com.mrkurilin.aethalides.data.room.repositories.DaysRoomRepository
import com.mrkurilin.aethalides.data.room.repositories.NotesRoomRepository
import com.mrkurilin.aethalides.data.room.repositories.PointsRoomRepository
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.repositories.EventsRoomRepository

class AethalidesApp : Application() {

    private lateinit var navController: NavController

    private val roomDatabase: AethalidesRoomDatabase by lazy {
        Room.databaseBuilder(
            this,
            AethalidesRoomDatabase::class.java,
            "Aethalides Room Database"
        ).allowMainThreadQueries().build()
    }

    private val notesRepository: NotesRepository by lazy {
        val dao = roomDatabase.getNotesDao()
        return@lazy NotesRoomRepository(dao)
    }

    private val eventsRepository: EventsRepository by lazy {
        val dao = roomDatabase.getEventsDao()
        return@lazy EventsRoomRepository(dao)
    }

    private val pointsRepository: PointsRepository by lazy {
        val dao = roomDatabase.getPointsDao()
        return@lazy PointsRoomRepository(dao)
    }

    private val daysRepository: DaysRepository by lazy {
        val dao = roomDatabase.getDayDao()
        return@lazy DaysRoomRepository(dao)
    }

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun provideNavController(): NavController {
        return navController
    }

    fun providePointsRepository(): PointsRepository {
        return pointsRepository
    }

    fun provideNotesRepository(): NotesRepository {
        return notesRepository
    }

    fun provideDaysRepository(): DaysRepository {
        return daysRepository
    }

    fun provideEventsRepository(): EventsRepository {
        return eventsRepository
    }
}