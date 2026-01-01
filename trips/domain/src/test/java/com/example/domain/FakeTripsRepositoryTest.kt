package com.example.domain

import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeTripsRepositoryTest(
    trips: List<Trip> = emptyList(),
    lastUpdated: Long? = null,
    lastError: String? = null,
) : TripsRepository {
    private val tripsFlow = MutableStateFlow(trips)
    private val lastUpdatedFlow = MutableStateFlow(lastUpdated)
    private val lastErrorFlow = MutableStateFlow(lastError)

    override fun observeTrips(): Flow<List<Trip>> = tripsFlow

    override fun observeTripsById(tripId: String): Flow<Trip?> = tripsFlow
        .map { list -> list.firstOrNull { it.id == tripId } }

    override fun observeTripsLastUpdated(): Flow<Long?> = lastUpdatedFlow

    override fun observeTripsLastError(): Flow<String?> = lastErrorFlow

    override suspend fun refreshTrips() = Unit
}
