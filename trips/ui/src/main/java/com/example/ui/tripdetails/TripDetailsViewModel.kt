package com.example.ui.tripdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.tripdetails.GetTripByIdUseCase
import com.example.ui.navigation.TRIP_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTripByIdUseCase: GetTripByIdUseCase,
) : ViewModel() {
    private val tripId = savedStateHandle.get<String>(TRIP_ID_ARG).orEmpty()
    private val mutableState = MutableStateFlow<TripDetailsState>(TripDetailsState.Loading)
    val state: MutableStateFlow<TripDetailsState>
        get() = mutableState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            runCatching {
                getTripByIdUseCase(tripId)
            }.onSuccess { trip ->
                state.value = TripDetailsState.Content(trip)
            }.onFailure { exception ->
                state.value = TripDetailsState.Error(exception.message ?: "Unknown Error")
            }
        }
    }
}
