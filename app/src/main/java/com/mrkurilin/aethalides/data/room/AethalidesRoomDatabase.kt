package com.mrkurilin.aethalides.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrkurilin.aethalides.data.room.daos.NotesDao
import com.mrkurilin.aethalides.data.room.daos.PointsDao
import com.mrkurilin.aethalides.data.room.entities.*

@Database(
    version = 1,
    entities = [
        EatenFoodRoomEntity::class,
        EventRoomEntity::class,
        NoteRoomEntity::class,
        PointRoomEntity::class,
        SpendingRoomEntity::class,
    ]
)
abstract class AethalidesRoomDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getPointsDao(): PointsDao
}