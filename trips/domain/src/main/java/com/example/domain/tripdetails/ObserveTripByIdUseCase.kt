package com.example.domain.tripdetails

import com.example.domain.trips.TripsRepository
import javax.inject.Inject

class ObserveTripByIdUseCase @Inject constructor(
    private val repository: TripsRepository,
) {
    suspend operator fun invoke(tripId: String) = repository.observeTripsById(tripId)
}
