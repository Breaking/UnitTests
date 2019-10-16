package com.krivosheev.mikhail.unittests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RandomNameGeneratorImplTest {

    private val randomNameGenerator = RandomNameGeneratorImpl()

    @Test
    fun `should return alphabetical characters`() {
        val actual = randomNameGenerator.generateRandomName()

        assertTrue(actual.matches(Regex("[a-z]{8}")))
    }

    @Test
    fun `should match length of the generated names`() {
        val actual = randomNameGenerator.generateRandomName()

        assertEquals(8, actual.length)
    }

    @Test
    fun `should return new name every call`() {
        val actual = randomNameGenerator.generateRandomName()

        assertNotEquals(randomNameGenerator.generateRandomName(), actual)
    }
}