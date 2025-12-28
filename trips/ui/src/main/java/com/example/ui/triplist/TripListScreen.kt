package com.example.ui.triplist

import androidx.compose.foundation.clickable
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
import com.example.domain.trips.Trip
import com.example.ui.R

@Composable
fun TripListScreen(
    state: TripListState,
    onTripClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TripListTopAppBar(
                title = stringResource(R.string.trips),
            )
        },
    ) { padding ->
        when (state) {
            is TripListState.Loading -> {
                Text(stringResource(R.string.loading))
            }

            is TripListState.Content -> {
                TripList(
                    trips = state.trips,
                    padding = padding,
                    onTripClick = onTripClick,
                )
            }

            is TripListState.Error -> {
                Text("Error: ${state.message}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TripListTopAppBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    )
}

@Composable
private fun TripList(
    trips: List<Trip>,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onTripClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        items(
            count = trips.size,
        ) { index ->
            Text(
                text = "${trips[index].title} ${trips[index].startDate} ${trips[index].endDate}",
                modifier = Modifier.clickable {
                    onTripClick(trips[index].id)
                },
            )
        }
    }
}
