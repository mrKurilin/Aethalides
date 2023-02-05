package com.mrkurilin.aethalides

import android.app.Application
import androidx.navigation.NavController
import androidx.room.Room
import com.mrkurilin.aethalides.data.repository.*
import com.mrkurilin.aethalides.data.room.AethalidesRoomDatabase
import com.mrkurilin.aethalides.data.room.repositories.*

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

    private val eatenFoodRepository: EatenFoodRepository by lazy {
        val dao = roomDatabase.getEatenFoodDao()
        return@lazy EatenFoodRoomRepository(dao)
    }

    private val spendingRepository: SpendingRepository by lazy {
        val dao = roomDatabase.getSpendingDao()
        return@lazy SpendingRoomRepository(dao)
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

    fun provideEatenFoodRepository(): EatenFoodRepository {
        return eatenFoodRepository
    }

    fun provideSpendingRepository(): SpendingRepository {
        return spendingRepository
    }
}