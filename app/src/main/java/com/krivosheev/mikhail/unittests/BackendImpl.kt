package com.krivosheev.mikhail.unittests

import android.util.SparseArray
import java.util.*

class BackendImpl : Backend {

    private companion object {

        private const val ID_TO_FAIL = 50
    }

    private val cache = SparseArray<Backend.Result>()

    override fun getName(
        id: Int,
        resultCallback: Backend.ResultCallback?,
        errorCallback: Backend.ErrorCallback?
    ) {
        require(id >= 0) {
            IllegalArgumentException("Id must be greater or equal than zero.")
        }

        if (id > ID_TO_FAIL) {
            errorCallback?.onError(
                IllegalArgumentException("Backend fails to get name for user with id=$id")
            )
        } else {
            val result = getResultFromCacheOrCreate(id)
            resultCallback?.onSuccess(result)
        }
    }

    private fun getResultFromCacheOrCreate(id: Int) =
        cache[id].takeIf { it != null } ?: {
            val name = generateRandomName()
            Backend.Result(name)
        }.invoke()
            .also { cache.put(id, it) }

    private fun generateRandomName(): String =
        (0..7).map { (97 + Random().nextInt(26)).toChar() }.joinToString(separator = "")
}