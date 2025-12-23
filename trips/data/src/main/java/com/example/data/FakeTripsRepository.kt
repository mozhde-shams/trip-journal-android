package com.example.data

import com.example.domain.Trip
import com.example.domain.TripsRepository
import java.time.LocalDate
import javax.inject.Inject

private const val FIRST_INDEX = 1
private const val DEFAULT_TRIPS_COUNT = 10
private const val DAYS_BETWEEN_TRIPS = 7
private const val TRIP_DURATION_DAYS = 4

class FakeTripsRepository @Inject constructor() : TripsRepository {
    override suspend fun getTrips(): List<Trip> {
        val base = LocalDate.now()
        return (FIRST_INDEX..DEFAULT_TRIPS_COUNT).map { index ->
            val start = base.minusDays((index * DAYS_BETWEEN_TRIPS).toLong())
            val end = start.plusDays(TRIP_DURATION_DAYS.toLong())
            Trip(
                id = index.toString(),
                title = "Trip $index",
                startDate = start,
                endDate = end,
            )
        }
    }
}
