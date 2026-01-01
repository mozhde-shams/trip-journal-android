package com.example.data.database.trip

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.sync.SyncMetaDao
import com.example.data.database.sync.SyncMetaEntity

@Database(
    entities = [
        TripEntity::class,
        SyncMetaEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class TripsDataBase : RoomDatabase() {
    abstract fun tripDao(): TripDao

    abstract fun syncMetaDao(): SyncMetaDao
}
