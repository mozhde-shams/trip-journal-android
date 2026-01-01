package com.example.domain

import com.example.domain.triplist.ObserveTripsLastErrorUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveTripsLastErrorUseCaseTest {
    @Test
    fun `returns whatever repository emits`() = runTest {
        val useCase = createUseCase("Remote Failure")
        val actual = useCase().first()
        assertEquals("Remote Failure", actual)
    }

    @Test
    fun `handle null error`() = runTest {
        val useCase = createUseCase(lastErrorStr = null)
        val actual = useCase().first()
        assertEquals(null, actual)
    }
}

private fun createUseCase(lastErrorStr: String?) = ObserveTripsLastErrorUseCase(
    tripsRepository = FakeTripsRepositoryTest(
        lastError = lastErrorStr,
    ),
)
