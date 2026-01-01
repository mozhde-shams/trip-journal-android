package com.example.data.remote

import com.example.data.PopulateInitialTrips
import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeTripsRemoteDataSource @Inject constructor(
    private val populateInitialTrips: PopulateInitialTrips,
    private val delay: Long = 300L,
    private val shouldFail: Boolean = false,
) : TripsRemoteDataSource {
    override suspend fun fetchTrips(): List<Trip> {
        delay(delay)
        if (shouldFail) {
            error("Remote failure")
        }
        return populateInitialTrips.populateTrips()
    }
}
