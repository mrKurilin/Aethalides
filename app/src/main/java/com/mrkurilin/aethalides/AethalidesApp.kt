package com.mrkurilin.aethalides

import android.app.Application
import androidx.navigation.NavController
import androidx.room.Room
import com.mrkurilin.aethalides.data.repository.NotesRepository
import com.mrkurilin.aethalides.data.repository.PointsRepository
import com.mrkurilin.aethalides.data.repository.roomRepositories.NotesRoomRepository
import com.mrkurilin.aethalides.data.repository.roomRepositories.PointsRoomRepository
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.RoomConstants

class AethalidesApp : Application() {

    private lateinit var navController: NavController

    private val roomDatabase: AethalidesRoomDatabase by lazy {
        Room.databaseBuilder(
            this,
            AethalidesRoomDatabase::class.java,
            RoomConstants.AETHALIDES_ROOM_DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    private val notesRepository: NotesRepository by lazy {
        val dao = roomDatabase.getNotesDao()
        return@lazy NotesRoomRepository(dao)
    }

    private val pointsRepository: PointsRepository by lazy {
        val dao = roomDatabase.getPointsDao()
        return@lazy PointsRoomRepository(dao)
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
}