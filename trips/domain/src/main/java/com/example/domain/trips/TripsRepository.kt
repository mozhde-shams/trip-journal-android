package com.example.domain.trips

import kotlinx.coroutines.flow.Flow

interface TripsRepository {
    suspend fun observeTrips(): Flow<List<Trip>>

    suspend fun observeTripsById(tripId: String): Flow<Trip?>

    suspend fun populateInitialTripsIfEmpty()
}
