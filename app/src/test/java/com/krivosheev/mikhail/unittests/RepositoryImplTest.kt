package com.krivosheev.mikhail.unittests

import android.util.SparseArray
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RepositoryImplTest {

    private val randomNameGenerator: RandomNameGeneratorImpl = mockk {
        every { generateRandomName() } returns RANDOM_NAME
    }
    private val cache = mockk<SparseArray<Backend.Result>>(relaxed = true)
    private val repository = RepositoryImpl(randomNameGenerator, cache)

    @Test
    fun `should return backend_result when getting result from cache given cache is empty`() {
        every { cache.get(ID) } returns null

        val result = repository.getResultFromCacheOrCreate(ID)

        assertEquals(RANDOM_NAME, result.name)
    }

    @Test
    fun `should generate random name when getting result from cache given cache is empty`() {
        every { cache.get(ID) } returns null

        repository.getResultFromCacheOrCreate(ID)

        verify { randomNameGenerator.generateRandomName() }
    }

    @Test
    fun `should put backend_result into cache when getting result from cache given cache is empty`() {
        every { cache.get(ID) } returns null

        val result = repository.getResultFromCacheOrCreate(ID)

        verify { cache.put(ID, result) }
    }

    @Test
    fun `should return backend_result when getting result from cache given cache is not empty`() {
        val expectedResult = Backend.Result(RANDOM_NAME)
        every { cache.get(ID) } returns expectedResult

        val result = repository.getResultFromCacheOrCreate(ID)

        assertEquals(expectedResult, result)
    }

    companion object {
        const val RANDOM_NAME = "random name"
        const val ID = 5
    }
}