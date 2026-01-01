package com.example.domain.triplist

import com.example.domain.trips.TripsRepository
import javax.inject.Inject

class ObserveTripsLastErrorUseCase @Inject constructor(
    private val tripsRepository: TripsRepository,
) {
    operator fun invoke() = tripsRepository.observeTripsLastError()
}
