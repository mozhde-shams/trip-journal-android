package com.example.domain

import com.example.domain.tripdetails.ObserveTripByIdUseCase
import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class GetTripByIdUseCaseTest {
    @Test
    fun `return trip when id exists`() = runTest {
        val trips = listOf(
            Trip(
                id = "1",
                title = "Berlin",
                startDate = LocalDate.of(2025, 1, 1),
                endDate = LocalDate.of(2025, 1, 5),
            ),
            Trip(
                id = "2",
                title = "Amsterdam",
                startDate = LocalDate.of(2025, 2, 1),
                endDate = LocalDate.of(2025, 2, 3),
            ),
        )

        val useCase = createUseCase(trips)
        val actual = useCase("1").first()

        assertEquals(trips[0], actual)
    }

    @Test
    fun `throws NoSuchElementException when id not found`() = runTest {
        val trips = listOf(
            Trip(
                id = "1",
                title = "Berlin",
                startDate = LocalDate.of(2025, 1, 1),
                endDate = LocalDate.of(2025, 1, 5),
            ),
            Trip(
                id = "2",
                title = "Amsterdam",
                startDate = LocalDate.of(2025, 2, 1),
                endDate = LocalDate.of(2025, 2, 3),
            ),
        )
        val useCase = createUseCase(trips)
        val result = useCase("999").first()
        assertNull(result)
    }
}

private class TripsRepositoryTest(
    private val initialTrips: List<Trip>,
) : TripsRepository {
    private val trips = MutableStateFlow(initialTrips)

    override suspend fun observeTrips(): Flow<List<Trip>> = trips

    override suspend fun observeTripsById(tripId: String): Flow<Trip?> = trips
        .map { list -> findTripById(list, tripId) }

    override suspend fun populateInitialTripsIfEmpty() = Unit

    private fun findTripById(
        list: List<Trip>,
        tripId: String,
    ): Trip? = list.firstOrNull { it.id == tripId }
}

private fun createUseCase(trips: List<Trip>) = ObserveTripByIdUseCase(TripsRepositoryTest(trips))
