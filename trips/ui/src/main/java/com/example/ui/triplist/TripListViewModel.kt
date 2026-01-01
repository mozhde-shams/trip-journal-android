package com.example.ui.triplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.triplist.ObserveTripListUseCase
import com.example.domain.triplist.ObserveTripsLastErrorUseCase
import com.example.domain.triplist.ObserveTripsLastUpdatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val observeTripListUseCase: ObserveTripListUseCase,
    private val observeTripsLastUpdatedUseCase: ObserveTripsLastUpdatedUseCase,
    private val observeTripsLastErrorUseCase: ObserveTripsLastErrorUseCase,
) : ViewModel() {
    private val mutableState = MutableStateFlow<TripListState>(TripListState.Loading)
    val state: MutableStateFlow<TripListState>
        get() = mutableState

    init {
        observeTrips()
        observeLastUpdated()
        observeLastError()
    }

    private fun observeTrips() {
        viewModelScope.launch {
            runCatching {
                observeTripListUseCase().collect { tripList ->
                    mutableState.value = currentContent().copy(trips = tripList)
                }
            }.onFailure { exception ->
                mutableState.value = TripListState.Error(exception.message ?: "Unknown Error")
            }
        }
    }

    private fun observeLastUpdated() {
        viewModelScope.launch {
            observeTripsLastUpdatedUseCase().collect { millis ->
                val lastUpdated = millis?.let { "Last updated: ${Instant.ofEpochMilli(it)}" }
                    ?: "Last updated: Never"
                mutableState.value = currentContent().copy(lastUpdatedText = lastUpdated)
            }
        }
    }

    private fun observeLastError() {
        viewModelScope.launch {
            observeTripsLastErrorUseCase().collect { message ->
                mutableState.value = currentContent().copy(lastError = message)
            }
        }
    }

    private fun currentContent(): TripListState.Content =
        (mutableState.value as? TripListState.Content) ?: TripListState.Content(trips = emptyList())
}
