package com.example.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetTripsUseCase
import com.example.ui.TripsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val getTripsUseCase: GetTripsUseCase,
) : ViewModel() {
    private val mutableState = MutableStateFlow<TripsState>(TripsState.Loading)
    val state: MutableStateFlow<TripsState>
        get() = mutableState

    init {
        fetchTrips()
    }

    private fun fetchTrips() {
        viewModelScope.launch {
            runCatching {
                getTripsUseCase()
            }.onSuccess { trips ->
                state.value = TripsState.Content(trips)
            }.onFailure { exception ->
                state.value = TripsState.Error(exception.message ?: "Unknown Error")
            }
        }
    }
}
