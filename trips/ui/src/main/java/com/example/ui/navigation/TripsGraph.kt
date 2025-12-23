package com.example.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

const val TRIPS_GRAPH_ROUTE = "trips_graph"

fun NavGraphBuilder.tripsGraph() {
    navigation(
        startDestination = TRIPS_ROUTE,
        route = TRIPS_GRAPH_ROUTE,
    ) {
        tripsScreen()
    }
}
