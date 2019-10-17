package com.krivosheev.mikhail.unittests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class RandomNameGeneratorImplTest {

    private val matcher = Regex("[a-z]{8}")
    private val randomNameGenerator = RandomNameGeneratorImpl()

    @Test
    fun `should match 8 alphabetical characters  when generating random name for every call`() {
        val actual = randomNameGenerator.generateRandomName()

        assertTrue(actual.matches(matcher))
    }

    @Test
    fun `should return new name when generating random name for every call`() {
        val actual1 = randomNameGenerator.generateRandomName()
        val actual2 = randomNameGenerator.generateRandomName()

        assertNotEquals(actual1, actual2)
    }
}