package com.example.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class GetTripsUseCaseTest {
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
        val actual = useCase()
        assertEquals(expected, actual)
    }

    @Test
    fun `handles empty list`() = runTest {
        val useCase = createUseCase(emptyList())
        val actual = useCase()
        assertTrue(actual.isEmpty())
    }
}

private class TestTripsRepository(
    private val trips: List<Trip> = emptyList(),
) : TripsRepository {
    override suspend fun getTrips(): List<Trip> = trips
}

private fun createUseCase(trips: List<Trip>) = GetTripsUseCase(TestTripsRepository(trips))
