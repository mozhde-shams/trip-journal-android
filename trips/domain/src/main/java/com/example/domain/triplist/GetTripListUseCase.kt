package com.example.domain.triplist

import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRepository
import javax.inject.Inject

class GetTripListUseCase @Inject constructor(
    private val tripsRepository: TripsRepository,
) {
    suspend operator fun invoke(): List<Trip> = tripsRepository.getTrips()
}
