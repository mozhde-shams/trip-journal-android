package com.example.ui

import com.example.domain.Trip

sealed interface TripsState {
    data object Loading : TripsState

    data class Content(
        val trips: List<Trip>,
    ) : TripsState

    data class Error(
        val message: String,
    ) : TripsState
}
