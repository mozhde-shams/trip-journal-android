package com.example.data

import com.example.data.database.TripDao
import com.example.data.database.TripEntity
import com.example.data.database.toDomain
import com.example.data.database.toEntity
import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRemoteDataSource
import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeTripsRepository @Inject constructor(
    private val dao: TripDao,
    private val remote: TripsRemoteDataSource,
) : TripsRepository {
    override fun observeTrips(): Flow<List<Trip>> = dao.observeTrips().map(::mapEntities)

    override fun observeTripsById(tripId: String): Flow<Trip?> = dao
        .observeTripById(tripId)
        .map(::mapEntityOrNull)

    override suspend fun refreshTrips() {
        val remoteTrips = remote.fetchTrips()
        dao.upsertAll(remoteTrips.map { it.toEntity() })
    }

    private fun mapEntities(entities: List<TripEntity>): List<Trip> = entities.map { it.toDomain() }

    private fun mapEntityOrNull(entity: TripEntity?): Trip? = entity?.toDomain()
}
