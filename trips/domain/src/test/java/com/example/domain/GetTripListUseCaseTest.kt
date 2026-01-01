package com.example.domain

import com.example.domain.triplist.ObserveTripListUseCase
import com.example.domain.trips.Trip
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class GetTripListUseCaseTest {
    @Test
    fun `returns whatever repository returns`() = runTest {
        val expected = listOf(
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

        val useCase = createUseCase(expected)
        val actual = useCase().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `handles empty list`() = runTest {
        val useCase = createUseCase(emptyList())
        val actual = useCase().first()
        assertTrue(actual.isEmpty())
    }
}

private fun createUseCase(trips: List<Trip>) = ObserveTripListUseCase(
    tripsRepository = FakeTripsRepositoryTest(trips),
)
