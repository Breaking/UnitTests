package com.krivosheev.mikhail.unittests

import android.util.SparseArray

class RepositoryImpl(
    private val randomNameGenerator: RandomNameGenerator,
    private val cache: SparseArray<Backend.Result>
) : Repository {

    override fun getResultFromCacheOrCreate(id: Int) =
        cache[id].takeIf { it != null } ?: {
            val name = randomNameGenerator.generateRandomName()
            Backend.Result(name)
        }.invoke()
            .also {
                cache.put(id, it)
            }
}

