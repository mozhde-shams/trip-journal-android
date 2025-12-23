package com.example.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.TripsScreen

const val TRIPS_ROUTE = "trips"

fun NavGraphBuilder.tripsScreen() {
    composable(route = TRIPS_ROUTE) {
        val tripsViewModel: TripsViewModel = hiltViewModel()
        val tripsState by tripsViewModel.state.collectAsState()
        TripsScreen(
            state = tripsState,
        )
    }
}
