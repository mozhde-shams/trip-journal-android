package com.example.tripjournal

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ui.navigation.TRIPS_GRAPH_ROUTE
import com.example.ui.navigation.tripsGraph

@Composable
fun AppHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TRIPS_GRAPH_ROUTE,
    ) {
        tripsGraph()
    }
}
