package com.example.ui.tripdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ui.R

@Composable
fun TripDetailsScreen(state: TripDetailsState) {
    TripDetailsContent(state)
}

@Composable
fun TripDetailsContent(state: TripDetailsState) {
    when (state) {
        is TripDetailsState.Loading -> {
            Text(stringResource(R.string.loading))
        }

        is TripDetailsState.Content -> {
            val trip = state.trip
            Card(modifier = Modifier.padding(16.dp)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(trip.title)
                    Text(trip.startDate.toString())
                    Text(trip.endDate.toString())
                }
            }
        }

        is TripDetailsState.Error -> {
            Text("Error: ${state.message}")
        }
    }
}
