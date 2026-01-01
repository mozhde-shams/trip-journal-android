package com.example.domain.trips

import kotlinx.coroutines.flow.Flow

interface TripsRepository {
    fun observeTrips(): Flow<List<Trip>>

    fun observeTripsById(tripId: String): Flow<Trip?>

    suspend fun refreshTrips()

    fun observeTripsLastUpdated(): Flow<Long?>

    fun observeTripsLastError(): Flow<String?>
}
