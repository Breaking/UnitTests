package com.krivosheev.mikhail.unittests.rx

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GetItemsListUseCaseImplTest {

    @get:Rule
    val testSchedulerRule = TestSchedulerRule()

    private val repositoryItemDetails = mockk<Repository.ItemDetails>()

    private val useCaseException = Exception()

    private val repositoryItemsSubject = SingleSubject.create<List<Repository.Item>>()
    private val repositoryItemDetailsSubject = SingleSubject.create<Repository.ItemDetails>()
    private val repository = mockk<Repository>()
    private val useCase = GetItemsListUseCaseImpl(repository)

    @Test
    fun `given all requests successful when executed then return appropriate list of detailed items`() {
        every { repository.getItems() } returns Single.just(stubItems())
        every { repository.getItemDetails(any()) } answers {
            Single.just(createStubDetailedItem(firstArg()))
        }

        useCase.getItemsWithDetails()
            .test()
            .assertNoErrors()
            .assertValue { it == createExpectedValues() }
    }

    @Test
    fun `given all requests fail when executed then emits error`() {
        val repository = mockk<Repository> {
            every { getItems() } returns repositoryItemsSubject
            every { getItemDetails(any()) } returns repositoryItemDetailsSubject
        }
        repositoryItemsSubject.onError(useCaseException)
        repositoryItemDetailsSubject.onError(useCaseException)
        val useCase = GetItemsListUseCaseImpl(repository)

        val subscriber = useCase.getItemsWithDetails().test()

        subscriber.assertError(useCaseException)
    }

    private fun stubItems() =
        listOf(
            Repository.Item(0),
            Repository.Item(1)
        )

    private fun createExpectedValues() =
        listOf(
            GetItemsListUseCase.DetailedItem(0, "Name0", "Type0"),
            GetItemsListUseCase.DetailedItem(1, "Name1", "Type1")
        )

    private fun createStubDetailedItem(id: Int) =
        Repository.ItemDetails(
            name = "Name$id",
            type = "Type$id"
        )
}