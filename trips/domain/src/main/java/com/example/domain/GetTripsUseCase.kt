package com.example.domain

import javax.inject.Inject

class GetTripsUseCase @Inject constructor(
    private val tripsRepository: TripsRepository,
) {
    suspend operator fun invoke(): List<Trip> = tripsRepository.getTrips()
}
