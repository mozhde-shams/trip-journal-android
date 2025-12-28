package com.example.domain

import com.example.domain.tripdetails.GetTripByIdUseCase
import com.example.domain.trips.Trip
import com.example.domain.trips.TripsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertFailsWith

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
        val actual = useCase("1")

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

        assertFailsWith<NoSuchElementException> {
            useCase("999")
        }
    }
}

private class TripsRepositoryTest(
    private val trips: List<Trip>,
) : TripsRepository {
    override suspend fun getTrips(): List<Trip> = trips

    override suspend fun getTripById(tripId: String): Trip = trips.first { it.id == tripId }
}

private fun createUseCase(trips: List<Trip>) = GetTripByIdUseCase(TripsRepositoryTest(trips))
