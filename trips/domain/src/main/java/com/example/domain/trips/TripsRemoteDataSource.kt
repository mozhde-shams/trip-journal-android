package com.example.domain.trips

interface TripsRemoteDataSource {
    suspend fun fetchTrips(): List<Trip>
}
