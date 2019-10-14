package com.krivosheev.mikhail.unittests

import java.util.*

class RandomNameGeneratorImpl(private val random: Random = Random()) : RandomNameGenerator {
//private val seed = System.currentTimeMillis()
    override fun generateRandomName(): String =
        (0..7).map { (97 + random.nextInt(26)).toChar() }.joinToString(separator = "")
}
// один тест мокаешь конструктор Random() мокаешь 1 вызово nextInt(5) и проверяешь что на выходе
// строка равна некоей результирующей
// например eeeeeeee
// тест что именно