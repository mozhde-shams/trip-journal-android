package com.example.ui.triplist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.trips.Trip
import com.example.ui.R

@Composable
fun TripListScreen(
    state: TripListState,
    onTripClick: (String) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val lastError = (state as? TripListState.Content)?.lastError

    LaunchedEffect(lastError) {
        if (!lastError.isNullOrBlank()) {
            snackBarHostState.showSnackbar(lastError)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
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
                TripListContent(
                    state = state,
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
private fun TripListContent(
    state: TripListState.Content,
    padding: PaddingValues,
    onTripClick: (String) -> Unit,
) {
    TripList(
        trips = state.trips,
        onTripClick = onTripClick,
        padding = padding,
        lastUpdatedText = state.lastUpdatedText,
    )
}

@Composable
private fun TripList(
    trips: List<Trip>,
    lastUpdatedText: String,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onTripClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        item {
            Text(
                text = lastUpdatedText,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            )
        }
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
