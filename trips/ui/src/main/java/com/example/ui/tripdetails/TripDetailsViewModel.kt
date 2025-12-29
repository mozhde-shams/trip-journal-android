package com.example.ui.tripdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.tripdetails.ObserveTripByIdUseCase
import com.example.ui.navigation.TRIP_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeTripByIdUseCase: ObserveTripByIdUseCase,
) : ViewModel() {
    private val tripId: String? = savedStateHandle.get<String>(TRIP_ID_ARG)
    private val mutableState = MutableStateFlow<TripDetailsState>(TripDetailsState.Loading)
    val state: MutableStateFlow<TripDetailsState>
        get() = mutableState

    init {
        onStart()
    }

    private fun onStart() {
        val id = requireTripIdOrShowError() ?: return
        observeTrip(id)
    }

    private fun requireTripIdOrShowError(): String? {
        val id = tripId
        return if (id.isNullOrBlank()) {
            mutableState.value = TripDetailsState.Error("Missing trip id")
            null
        } else {
            id
        }
    }

    private fun observeTrip(id: String) {
        viewModelScope.launch {
            runCatching {
                observeTripByIdUseCase(id).collect { trip ->
                    mutableState.value =
                        if (trip == null) {
                            TripDetailsState.Error("Trip not found")
                        } else {
                            TripDetailsState.Content(trip)
                        }
                }
            }.onFailure { e ->
                mutableState.value = TripDetailsState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
