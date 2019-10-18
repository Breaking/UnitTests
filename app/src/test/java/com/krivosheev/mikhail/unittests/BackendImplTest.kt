package com.krivosheev.mikhail.unittests

import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BackendImplTest {

    private val repository: Repository = mockk()
    private val backend = BackendImpl(repository)
    private val resultCallback: Backend.ResultCallback = mockk(relaxUnitFun = true)
    private val errorCallback: Backend.ErrorCallback = mockk(relaxUnitFun = true)
    private val backendResult: Backend.Result = mockk()

    @Test
    fun `should throw an exception when getting name given id less than zero`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            backend.getName(ID_LESS_THAN_ZERO, resultCallback, errorCallback)
        }

        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `should not invoke errorcallback when getting name given id less than zero`() {
        assertThrows(IllegalArgumentException::class.java) {
            backend.getName(ID_LESS_THAN_ZERO, resultCallback, errorCallback)
        }

        verify { errorCallback wasNot Called }
    }

    @Test
    fun `should not invoke resultcallback when getting name given id less than zero`() {
        assertThrows(IllegalArgumentException::class.java) {
            backend.getName(ID_LESS_THAN_ZERO, resultCallback, errorCallback)
        }

        verify { resultCallback wasNot Called }
    }

    @Test
    fun `should invoke errorcallback when getting name given id is greater than 50`() {
        val id = ID_MORE_THAN_FIFTY

        backend.getName(ID_MORE_THAN_FIFTY, resultCallback, errorCallback)

        verify {
            errorCallback.onError(match {
                it.javaClass == IllegalArgumentException::class.java &&
                        it.message == "Backend fails to get name for user with id=$id"
            })
        }
    }

    @Test
    fun `should not invoke resultcallback when getting name given id is greater than 50`() {
        backend.getName(ID_MORE_THAN_FIFTY, resultCallback, errorCallback)

        verify { resultCallback wasNot Called }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 49, 50])
    fun `should invoke resultcallback with success and correct result when getting name given id from range 0-50`(id: Int) {
        every { repository.getResultFromCacheOrCreate(id) } returns backendResult

        backend.getName(id, resultCallback, errorCallback)

        verify { resultCallback.onSuccess(backendResult) }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 49, 50])
    fun `should not invoke errorcallback when getting name given id from range 0-50`(id: Int) {
        every { repository.getResultFromCacheOrCreate(id) } returns backendResult

        backend.getName(id, resultCallback, errorCallback)

        verify { errorCallback wasNot Called }
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "java.lang.IllegalArgumentException: Id must be greater or equal than zero."
        private const val ID_LESS_THAN_ZERO = -1
        private const val ID_MORE_THAN_FIFTY = 51
    }
}