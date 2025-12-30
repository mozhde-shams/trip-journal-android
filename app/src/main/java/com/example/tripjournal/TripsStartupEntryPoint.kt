package com.example.tripjournal

import com.example.domain.triplist.RefreshTripsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TripsStartupEntryPoint {
    fun refreshTripsUseCase(): RefreshTripsUseCase
}
