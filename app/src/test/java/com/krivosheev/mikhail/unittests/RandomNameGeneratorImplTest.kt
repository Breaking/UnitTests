package com.krivosheev.mikhail.unittests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class RandomNameGeneratorImplTest {

    private val randomNameGenerator = RandomNameGeneratorImpl()

    @Test
    fun `should match 8 alphabetical characters when generating random name`() {
        val actual = randomNameGenerator.generateRandomName()

        assertTrue(actual.matches(matcher))
    }

    @Test
    fun `should return new name when generating random name`() {
        val expected = randomNameGenerator.generateRandomName()

        val actual = randomNameGenerator.generateRandomName()

        assertNotEquals(expected, actual)
    }

    companion object {
        private val matcher = Regex("[a-z]{8}")
    }
}