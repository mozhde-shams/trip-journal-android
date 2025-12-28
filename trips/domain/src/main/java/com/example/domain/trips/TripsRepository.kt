package com.example.domain.trips

interface TripsRepository {
    suspend fun getTrips(): List<Trip>

    suspend fun getTripById(tripId: String): Trip
}
