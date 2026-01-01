package com.example.domain

import com.example.domain.tripdetails.ObserveTripByIdUseCase
import com.example.domain.trips.Trip
import kotlinx.coroutines.flow.first
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

private fun createUseCase(trips: List<Trip>) = ObserveTripByIdUseCase(
    repository = FakeTripsRepositoryTest(
        trips = trips,
    ),
)
