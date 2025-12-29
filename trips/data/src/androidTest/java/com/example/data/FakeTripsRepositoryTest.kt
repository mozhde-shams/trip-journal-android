package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.database.PopulateInitialTrips
import com.example.data.database.TripDao
import com.example.data.database.TripsDataBase
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
    private lateinit var repo: FakeTripsRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TripsDataBase::class.java).allowMainThreadQueries().build()
        dao = db.tripDao()

        val populateInitialTrips = PopulateInitialTrips()
        repo = FakeTripsRepository(
            dao = dao,
            populateInitialTrips = populateInitialTrips,
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun observeTrips_emitsPopulatedTrips_afterPopulateIfEmpty() = runTest {
        repo.populateInitialTripsIfEmpty()
        val trips = repo.observeTrips().first()
        assert(trips.isNotEmpty())
    }

    @Test
    fun observeTripsById_emitUniqueId() = runTest {
        val trips = repo.observeTrips().first()
        val ids = trips.map { list -> list.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun observeTrips_eachTripHasValidDates() = runTest {
        val trips = repo.observeTrips().first()
        assertTrue(trips.all { it.startDate <= it.endDate })
    }

    @Test
    fun getTripById_returnMatchingTrip() = runTest {
        repo.populateInitialTripsIfEmpty()
        val result = repo.observeTripsById("1").first()
        assertEquals("1", result?.id.toString())
        assertEquals("Trip 1", result?.title.toString())
    }

    @Test
    fun getTripById_returnNullWhenTripIsNotFound() = runTest {
        repo.populateInitialTripsIfEmpty()
        val result = repo.observeTripsById("999").first()
        assertNull(result)
    }
}