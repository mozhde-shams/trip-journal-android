package com.example.data

import com.example.domain.TripsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class TripsDataModule {
    @Binds
    abstract fun bindUsersRepository(fakeTripsRepository: FakeTripsRepository): TripsRepository
}
