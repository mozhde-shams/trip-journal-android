package com.example.domain.triplist

import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTripListUseCase @Inject constructor(
    private val tripsRepository: TripsRepository,
) {
    operator fun invoke(): Flow<List<Trip>> = tripsRepository.observeTrips()
}
