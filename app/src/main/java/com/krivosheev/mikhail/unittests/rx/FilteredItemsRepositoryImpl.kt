package com.krivosheev.mikhail.unittests.rx

import com.krivosheev.mikhail.unittests.rx.GetItemsListUseCase.DetailedItem
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

private const val USER_INPUT_DELAY_MS = 300L

class FilteredItemsRepositoryImpl(private val getItemsListUseCase: GetItemsListUseCase) :
    FilteredItemsRepository {

    private val querySubject = BehaviorSubject.createDefault<String>("")

    override fun getFilteredItems(): Observable<List<DetailedItem>> =
        getItemsListUseCase
            .getItemsWithDetails()
            .switchMap(this::filterByQuery)

    private fun filterByQuery(items: List<DetailedItem>): Observable<List<DetailedItem>> =
        querySubject
            .debounce(USER_INPUT_DELAY_MS, TimeUnit.MILLISECONDS)
            .map { query ->
                items.filter { item ->
                    query.isEmpty() || item.name.contains(query)
                }
            }

    override fun search(query: String) {
        querySubject.onNext(query)
    }
}