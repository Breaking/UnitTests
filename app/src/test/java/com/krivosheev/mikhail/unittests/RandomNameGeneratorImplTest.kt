package com.krivosheev.mikhail.unittests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class RandomNameGeneratorImplTest {

    private val matcher = Regex("[a-z]{8}")
    private val randomNameGenerator = RandomNameGeneratorImpl()

    @Test
    fun `should match alphabetical characters when generating random name for every call`() {
        val actual = randomNameGenerator.generateRandomName()

        assertTrue(actual.matches(matcher))
    }

    @Test
    fun `should match length of the generated names when generating random name for every call`() {
        val actual = randomNameGenerator.generateRandomName()

        assertEquals(8, actual.length)
    }

    @Test
    fun `should return new name when generating random name for every call`() {
        val actual = randomNameGenerator.generateRandomName()

        assertNotEquals(randomNameGenerator.generateRandomName(), actual)
    }
}