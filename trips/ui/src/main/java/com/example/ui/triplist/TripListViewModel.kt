package com.example.ui.triplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.triplist.ObserveTripListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val observeTripListUseCase: ObserveTripListUseCase,
) : ViewModel() {
    private val mutableState = MutableStateFlow<TripListState>(TripListState.Loading)
    val state: MutableStateFlow<TripListState>
        get() = mutableState

    init {
        observeTrips()
    }

    private fun observeTrips() {
        viewModelScope.launch {
            runCatching {
                observeTripListUseCase().collect { tripList ->
                    mutableState.value = TripListState.Content(tripList)
                }
            }.onFailure { exception ->
                mutableState.value = TripListState.Error(exception.message ?: "Unknown Error")
            }
        }
    }
}
