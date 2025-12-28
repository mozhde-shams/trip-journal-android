package com.example.ui.triplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.triplist.GetTripListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val getTripListUseCase: GetTripListUseCase,
) : ViewModel() {
    private val mutableState = MutableStateFlow<TripListState>(TripListState.Loading)
    val state: MutableStateFlow<TripListState>
        get() = mutableState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            runCatching {
                getTripListUseCase()
            }.onSuccess { trips ->
                state.value = TripListState.Content(trips)
            }.onFailure { exception ->
                state.value = TripListState.Error(exception.message ?: "Unknown Error")
            }
        }
    }
}
