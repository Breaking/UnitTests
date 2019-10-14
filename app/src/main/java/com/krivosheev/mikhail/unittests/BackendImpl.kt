package com.krivosheev.mikhail.unittests

class BackendImpl(private val repository: Repository) : Backend {

    private companion object {
        private const val ID_TO_FAIL = 50
    }

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
            val result = repository.getResultFromCacheOrCreate(id)
            resultCallback?.onSuccess(result)
        }
    }
}