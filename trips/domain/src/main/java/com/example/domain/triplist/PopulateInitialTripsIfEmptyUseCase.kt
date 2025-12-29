package com.example.domain.triplist

import com.example.domain.trips.TripsRepository
import javax.inject.Inject

class PopulateInitialTripsIfEmptyUseCase @Inject constructor(
    private val repository: TripsRepository,
) {
    suspend operator fun invoke() = repository.populateInitialTripsIfEmpty()
}
