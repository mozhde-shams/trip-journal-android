package com.example.ui.triplist

import com.example.domain.trips.Trip

sealed interface TripListState {
    data object Loading : TripListState

    data class Content(
        val trips: List<Trip>,
    ) : TripListState

    data class Error(
        val message: String,
    ) : TripListState
}
