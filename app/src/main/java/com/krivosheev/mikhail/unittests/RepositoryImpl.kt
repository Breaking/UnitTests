package com.krivosheev.mikhail.unittests

import android.util.SparseArray

class RepositoryImpl(
    private val randomNameGenerator: RandomNameGenerator
) : Repository {
    var cache = SparseArray<Backend.Result>()
    //private val cache = SparseArray<Backend.Result>()

    override fun getResultFromCacheOrCreate(id: Int) =
        //cache[id].name
        cache[id].takeIf { it != null } ?: {
            val name = randomNameGenerator.generateRandomName()
            Backend.Result(name)
        }.invoke()
            .also { cache.put(id, it) }
}


// 2 теста
//1 тест в кеше пусто
