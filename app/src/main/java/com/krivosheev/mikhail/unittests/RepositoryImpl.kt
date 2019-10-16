package com.krivosheev.mikhail.unittests

import android.util.SparseArray

class RepositoryImpl(
    private val randomNameGenerator: RandomNameGenerator,
    private val cache: SparseArray<Backend.Result>
) : Repository {
    // var cache = SparseArray<Backend.Result>()

    override fun getResultFromCacheOrCreate(id: Int): Backend.Result {
        //cache[id].name
        //randomNameGenerator.generateRandomName()
        return cache[id].takeIf { it != null } ?: {
            val name = randomNameGenerator.generateRandomName()
            Backend.Result(name)
        }.invoke()
            .also {
                cache.put(id, it)
            }
    }

}


// 2 теста
//1 тест в кеше пусто
