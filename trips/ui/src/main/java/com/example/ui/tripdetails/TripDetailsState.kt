package com.example.ui.tripdetails

import com.example.domain.trips.Trip

sealed interface TripDetailsState {
    data object Loading : TripDetailsState

    data class Content(
        val trip: Trip,
    ) : TripDetailsState

    data class Error(
        val message: String,
    ) : TripDetailsState
}
