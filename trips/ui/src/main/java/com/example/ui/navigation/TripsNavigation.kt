package com.example.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ui.tripdetails.TripDetailsScreen
import com.example.ui.tripdetails.TripDetailsViewModel
import com.example.ui.triplist.TripListScreen
import com.example.ui.triplist.TripListViewModel

const val TRIPS_ROUTE = "trips"
const val TRIP_DETAILS_ROUTE = "trip_details"

const val TRIP_ID_ARG = "tripId"
const val TRIP_DETAILS_PATTERN = "$TRIP_DETAILS_ROUTE/{$TRIP_ID_ARG}"

fun NavGraphBuilder.tripsScreen(navController: NavHostController) {
    composable(route = TRIPS_ROUTE) {
        val tripsViewModel: TripListViewModel = hiltViewModel()
        val tripsState by tripsViewModel.state.collectAsState()
        TripListScreen(
            state = tripsState,
            onTripClick = { tripId ->
                navController.navigate(
                    route = tripDetailsRoute(
                        tripId = tripId,
                    ),
                )
            },
        )
    }
}

fun NavGraphBuilder.tripDetailsScreen() {
    composable(
        route = TRIP_DETAILS_PATTERN,
        arguments = listOf(
            navArgument(
                name = TRIP_ID_ARG,
            ) { type = NavType.StringType },
        ),
    ) {
        val tripDetailsViewModel: TripDetailsViewModel = hiltViewModel()
        val tripDetailsState by tripDetailsViewModel.state.collectAsState()
        TripDetailsScreen(
            state = tripDetailsState,
        )
    }
}

fun tripDetailsRoute(tripId: String): String = "$TRIP_DETAILS_ROUTE/$tripId"
