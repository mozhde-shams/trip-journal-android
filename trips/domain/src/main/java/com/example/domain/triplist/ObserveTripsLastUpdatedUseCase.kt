package com.example.domain.triplist

import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTripsLastUpdatedUseCase @Inject constructor(
    private val tripsRepository: TripsRepository,
) {
    operator fun invoke(): Flow<Long?> = tripsRepository.observeTripsLastUpdated()
}
