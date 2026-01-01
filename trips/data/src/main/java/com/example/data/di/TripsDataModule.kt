package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.FakeTripsRepository
import com.example.data.PopulateInitialTrips
import com.example.data.database.sync.SyncMetaDao
import com.example.data.database.trip.TripDao
import com.example.data.database.trip.TripsDataBase
import com.example.data.remote.FakeTripsRemoteDataSource
import com.example.domain.trips.TripsRemoteDataSource
import com.example.domain.trips.TripsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TripsDataModule {
    @Provides
    @Singleton
    fun provideTripsDatabase(
        @ApplicationContext context: Context,
    ): TripsDataBase = Room
        .databaseBuilder(
            context = context,
            klass = TripsDataBase::class.java,
            name = "trips.db",
        ).build()

    @Provides
    fun provideTripDao(db: TripsDataBase): TripDao = db.tripDao()

    @Provides
    fun provideSyncMetaDao(db: TripsDataBase): SyncMetaDao = db.syncMetaDao()

    @Provides
    @Singleton
    fun provideTripsRepository(
        dao: TripDao,
        syncMetaDao: SyncMetaDao,
        remote: TripsRemoteDataSource,
    ): TripsRepository = FakeTripsRepository(
        dao = dao,
        syncMetaDao = syncMetaDao,
        remote = remote,
    )

    @Provides
    @Singleton
    fun provideTripsRemoteDataSource(populateInitialTrips: PopulateInitialTrips): TripsRemoteDataSource =
        FakeTripsRemoteDataSource(populateInitialTrips)
}
