package com.example.data

import com.example.data.database.sync.KEY_TRIPS_LAST_ERROR
import com.example.data.database.sync.KEY_TRIPS_LAST_UPDATED
import com.example.data.database.sync.SyncMetaDao
import com.example.data.database.sync.SyncMetaEntity
import com.example.data.database.trip.TripDao
import com.example.data.database.trip.TripEntity
import com.example.data.database.trip.toDomain
import com.example.data.database.trip.toEntity
import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRemoteDataSource
import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeTripsRepository @Inject constructor(
    private val dao: TripDao,
    private val syncMetaDao: SyncMetaDao,
    private val remote: TripsRemoteDataSource,
) : TripsRepository {
    override fun observeTrips(): Flow<List<Trip>> = dao.observeTrips().map(::mapEntities)

    override fun observeTripsById(tripId: String): Flow<Trip?> = dao
        .observeTripById(tripId)
        .map(::mapEntityOrNull)

    override suspend fun refreshTrips() {
        runCatching {
            val remoteTrips = remote.fetchTrips()
            dao.upsertAll(remoteTrips.map { it.toEntity() })

            syncMetaDao.upsert(
                SyncMetaEntity(
                    key = KEY_TRIPS_LAST_UPDATED,
                    longValue = System.currentTimeMillis(),
                    stringValue = null,
                ),
            )
            syncMetaDao.upsert(
                SyncMetaEntity(
                    key = KEY_TRIPS_LAST_ERROR,
                    longValue = null,
                    stringValue = null,
                ),
            )
        }.onFailure { exception ->
            syncMetaDao.upsert(
                SyncMetaEntity(
                    key = KEY_TRIPS_LAST_ERROR,
                    longValue = null,
                    stringValue = exception.message ?: "Refresh failed",
                ),
            )
            throw exception
        }
    }

    override fun observeTripsLastUpdated(): Flow<Long?> = syncMetaDao
        .observe(KEY_TRIPS_LAST_UPDATED)
        .map { it?.longValue }

    override fun observeTripsLastError(): Flow<String?> = syncMetaDao
        .observe(KEY_TRIPS_LAST_ERROR)
        .map { it?.stringValue }

    private fun mapEntities(entities: List<TripEntity>): List<Trip> = entities.map { it.toDomain() }

    private fun mapEntityOrNull(entity: TripEntity?): Trip? = entity?.toDomain()
}
