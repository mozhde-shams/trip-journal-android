package com.example.data

import com.example.domain.TripsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeTripsRepositoryTest {
    private val repo: TripsRepository = FakeTripsRepository()

    @Test
    fun `returns trips with unique ids`() = runTest {
        val trips = repo.getTrips()
        val ids = trips.map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun `returns trips with non blank and unique titles`() = runTest {
        val trips = repo.getTrips()
        assertTrue(trips.all { it.title.isNotBlank() })
        val titles = trips.map { it.title }
        assertEquals(titles.size, titles.distinct().size)
    }

    @Test
    fun `each trip has a valid date range`() = runTest {
        val trips = repo.getTrips()
        assertTrue(trips.all { it.startDate <= it.endDate })
    }
}
