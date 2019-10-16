package com.krivosheev.mikhail.unittests

import java.util.*

class RandomNameGeneratorImpl() : RandomNameGenerator {
    override fun generateRandomName(): String =
        (0..7).map { (97 + Random().nextInt(26)).toChar() }.joinToString(separator = "")
}