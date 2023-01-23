package com.mrkurilin.aethalides.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrkurilin.aethalides.data.room.entities.NoteRoomEntity
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity

@Database(
    version = 1,
    entities = [
        NoteRoomEntity::class,
        PointRoomEntity::class
    ]
)
abstract class AethalidesRoomDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getPointsDao(): PointsDao
}