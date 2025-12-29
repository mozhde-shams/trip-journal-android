package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TripEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class TripsDataBase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}
