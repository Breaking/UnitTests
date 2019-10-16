package com.krivosheev.mikhail.unittests

import io.mockk.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.IllegalArgumentException

class BackendImplTest {

    private val repository: Repository = mockk()
    private val backend = BackendImpl(repository)
    private val resultCallback: Backend.ResultCallback = mockk(relaxUnitFun = true)
    private val errorCallback: Backend.ErrorCallback = mockk(relaxUnitFun = true)

    @ParameterizedTest
    @ValueSource(ints = [-1, -5])
    fun `should show exception when getting name for given id less than zero`(id: Int) {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            backend.getName(id, resultCallback, errorCallback)
        }

        assertEquals(
            "java.lang.IllegalArgumentException: Id must be greater or equal than zero.",
            exception.message
        )

        verify { errorCallback wasNot Called }
        verify { resultCallback wasNot Called }
    }

    @ParameterizedTest
    @ValueSource(ints = [51, 100])
    fun `should invoke errorcallback when getting name for given id is greater than 50`(id: Int) {
        backend.getName(id, resultCallback, errorCallback)

        verify {
            errorCallback.onError(match {
                it.javaClass == IllegalArgumentException::class.java &&
                        it.message == "Backend fails to get name for user with id=$id"
            })
        }
        confirmVerified(errorCallback)
        verify { resultCallback wasNot Called }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 25, 49, 50])
    fun `should invoke resultCallback with success and correct result when getting name for given valid id`(
        id: Int
    ) {
        val result: Backend.Result = mockk()
        every { repository.getResultFromCacheOrCreate(id) } returns result

        backend.getName(id, resultCallback, errorCallback)

        verify {
            resultCallback.onSuccess(result)
        }
        confirmVerified(resultCallback)
        verify { errorCallback wasNot Called }
    }

}