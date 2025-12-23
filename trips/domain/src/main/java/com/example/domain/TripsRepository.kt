package com.example.domain

interface TripsRepository {
    suspend fun getTrips(): List<Trip>
}
