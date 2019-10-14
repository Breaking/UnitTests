package com.krivosheev.mikhail.unittests

import android.util.SparseArray
import androidx.core.util.set
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.*

class RepositoryImplTest {
    private val randomNameGenerator: RandomNameGeneratorImpl = mockk()
    private val repository = RepositoryImpl(randomNameGenerator)
    private val cache: SparseArray<Backend.Result> = mockk(relaxed = true)

    @Test
    fun `should return backend_result when cache is empty`() {
        val id = 5
        every { cache.get(id) } returns null
        every { randomNameGenerator.generateRandomName() } returns RANDOM_NAME

        repository.cache = cache
        val result = repository.getResultFromCacheOrCreate(id)

        verify { randomNameGenerator.generateRandomName() }
        verify { cache.put(id, result) }
        assertEquals(RANDOM_NAME, result.name)
    }

    @Test
    fun `should return backend_result when cache is not empty`() {
        val id = 15
        val expectedResult = Backend.Result(RANDOM_NAME)
        every { cache.get(id) } returns expectedResult

        repository.cache = cache
        val result = repository.getResultFromCacheOrCreate(id)

        assertEquals(expectedResult, result)
    }


    companion object {
        const val RANDOM_NAME = "random name"
    }
}