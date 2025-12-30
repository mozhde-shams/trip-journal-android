package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.PopulateInitialTrips
import com.example.data.database.TripDao
import com.example.data.database.TripsDataBase
import com.example.data.remote.FakeTripsRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FakeTripsRepositoryTest {
    private lateinit var dao: TripDao
    private lateinit var db: TripsDataBase
    private lateinit var remote: FakeTripsRemoteDataSource
    private lateinit var repo: FakeTripsRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = TripsDataBase::class.java,
            ).allowMainThreadQueries()
            .build()
        dao = db.tripDao()

        remote = FakeTripsRemoteDataSource(
            populateInitialTrips = PopulateInitialTrips(),
            delay = 0,
            shouldFail = false,
        )
        repo = FakeTripsRepository(
            dao = dao,
            remote = remote,
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun refreshTris_updatesDataBase() = runTest {
        repo.refreshTrips()
        val trips = repo.observeTrips().first()
        assert(trips.isNotEmpty())
    }

    @Test
    fun refreshTrips_failure_doesNotWipeCache() = runTest {
        repo.refreshTrips()
        val before = repo.observeTrips().first()
        assertTrue(before.isNotEmpty())

        val failingRemote = FakeTripsRemoteDataSource(
            populateInitialTrips = PopulateInitialTrips(),
            delay = 0,
            shouldFail = true,
        )
        repo = FakeTripsRepository(dao = dao, remote = failingRemote)

        runCatching { repo.refreshTrips() }

        val after = repo.observeTrips().first()
        assertEquals(before.map { it.id }.toSet(), after.map { it.id }.toSet())
    }

    @Test
    fun observeTripsById_emitUniqueId() = runTest {
        repo.refreshTrips()
        val trips = repo.observeTrips().first()
        val ids = trips.map { list -> list.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun observeTrips_eachTripHasValidDates() = runTest {
        repo.refreshTrips()
        val trips = repo.observeTrips().first()
        assertTrue(trips.all { it.startDate <= it.endDate })
    }

    @Test
    fun getTripById_returnMatchingTrip() = runTest {
        repo.refreshTrips()
        val result = repo.observeTripsById("1").first()
        assertEquals("1", result?.id.toString())
        assertEquals("Trip 1", result?.title.toString())
    }

    @Test
    fun getTripById_returnNullWhenTripIsNotFound() = runTest {
        repo.refreshTrips()
        val result = repo.observeTripsById("999").first()
        assertNull(result)
    }
}