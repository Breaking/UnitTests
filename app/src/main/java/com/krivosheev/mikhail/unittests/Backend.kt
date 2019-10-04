package com.krivosheev.mikhail.unittests

interface Backend {

    fun getName(
        id: Int,
        resultCallback: ResultCallback? = null,
        errorCallback: ErrorCallback? = null
    )

    /**
     * Class, which represents successful result.
     */
    data class Result(val name: String)

    /**
     * Interface of a callback, which is invoked in case of success.
     */
    interface ResultCallback {

        fun onSuccess(result: Result)
    }

    /**
     * Interface of a callback, which is invoked in case of error.
     */
    interface ErrorCallback {

        fun onError(throwable: Throwable)
    }
}