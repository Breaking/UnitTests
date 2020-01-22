package com.krivosheev.mikhail.unittests.rx

import com.krivosheev.mikhail.unittests.rx.GetItemsListUseCase.DetailedItem
import io.reactivex.Observable


interface FilteredItemsRepository {

    fun getFilteredItems(): Observable<List<DetailedItem>>

    fun search(query: String)
}