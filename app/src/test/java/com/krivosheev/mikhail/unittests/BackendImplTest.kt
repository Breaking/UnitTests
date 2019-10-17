package com.krivosheev.mikhail.unittests

import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BackendImplTest {

    private val repository: Repository = mockk()
    private val backend = BackendImpl(repository)
    private val resultCallback: Backend.ResultCallback = mockk(relaxUnitFun = true)
    private val errorCallback: Backend.ErrorCallback = mockk(relaxUnitFun = true)
    private val backendResult: Backend.Result = mockk()

    @ParameterizedTest
    @ValueSource(ints = [-1])
    fun `should show exception when getting name given id less than zero`(id: Int) {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            backend.getName(id, resultCallback, errorCallback)
        }

        assertEquals(
            "java.lang.IllegalArgumentException: Id must be greater or equal than zero.",
            exception.message
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [-1])
    fun `should not invoke errorcallback when getting name given id less than zero`(id: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            backend.getName(id, resultCallback, errorCallback)
        }

        verify { errorCallback wasNot Called }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1])
    fun `should not invoke resultcallback when getting name given id less than zero`(id: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            backend.getName(id, resultCallback, errorCallback)
        }

        verify { resultCallback wasNot Called }
    }

    @ParameterizedTest
    @ValueSource(ints = [51])
    fun `should invoke errorcallback when getting name given id is greater than 50`(id: Int) {
        backend.getName(id, resultCallback, errorCallback)

        verify {
            errorCallback.onError(match {
                it.javaClass == IllegalArgumentException::class.java &&
                        it.message == "Backend fails to get name for user with id=$id"
            })
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [51])
    fun `should not invoke resultcallback when getting name given id is greater than 50`(id: Int) {
        backend.getName(id, resultCallback, errorCallback)

        verify { resultCallback wasNot Called }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 49, 50])
    fun `should invoke resultcallback with success and correct result when getting name given valid id`(
        id: Int
    ) {
        every { repository.getResultFromCacheOrCreate(id) } returns backendResult

        backend.getName(id, resultCallback, errorCallback)

        verify {
            resultCallback.onSuccess(backendResult)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 49, 50])
    fun `should not invoke errorCallback when getting name given valid id`(
        id: Int
    ) {
        every { repository.getResultFromCacheOrCreate(id) } returns backendResult

        backend.getName(id, resultCallback, errorCallback)

        verify { errorCallback wasNot Called }
    }
}