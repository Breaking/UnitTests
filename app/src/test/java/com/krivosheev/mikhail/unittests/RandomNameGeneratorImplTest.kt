package com.krivosheev.mikhail.unittests

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.util.*

class RandomNameGeneratorImplTest {

    private val mockRandom: Random = mockk()
    private val randomNameGeneratorWithMockRandom = RandomNameGeneratorImpl(mockRandom)
    private val randomNameGenerator = RandomNameGeneratorImpl()

    @Test
    fun `should return alphabetical characters`() {
        every { mockRandom.nextInt(26) } returnsMany listOf(0, 1, 5, 10, 13, 20, 24, 25)

        assertEquals("abfknuyz", randomNameGeneratorWithMockRandom.generateRandomName())
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
    /*@Test
    fun `should return alphabetical characters`(){
        mockkConstructor(Random::class)
        every { anyConstructed<Random>().nextInt(26) } returnsMany listOf(0, 1, 5, 10, 13, 20, 24, 25)
        assertEquals("ABEJMUYZ", RandomNameGeneratorImpl().generateRandomName() )

    }*/
}