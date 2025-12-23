package com.example.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.Trip

@Composable
fun TripsScreen(state: TripsState) {
    Scaffold(
        topBar = {
            TripsTopAppBar(
                title = stringResource(R.string.trips),
            )
        },
    ) { padding ->
        when (state) {
            is TripsState.Loading -> {
                Text("Loading")
            }

            is TripsState.Content -> {
                TripsList(
                    trips = state.trips,
                    padding = padding,
                )
            }

            is TripsState.Error -> {
                Text("Error: ${state.message}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TripsTopAppBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    )
}

@Composable
private fun TripsList(
    trips: List<Trip>,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        items(
            count = trips.size,
        ) { index ->
            Text("${trips[index].title} ${trips[index].startDate} ${trips[index].endDate}")
        }
    }
}
