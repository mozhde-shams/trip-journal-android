package com.example.data.database.trip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val id: String,
    val title: String,
    val startDateDay: Long,
    val endDateDay: Long,
)
