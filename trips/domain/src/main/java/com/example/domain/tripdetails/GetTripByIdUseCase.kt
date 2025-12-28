package com.example.domain.tripdetails

import com.example.domain.trips.TripsRepository
import javax.inject.Inject

class GetTripByIdUseCase @Inject constructor(
    private val repository: TripsRepository,
) {
    suspend operator fun invoke(tripId: String) = repository.getTripById(tripId)
}
