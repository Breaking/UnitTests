package com.krivosheev.mikhail.unittests.rx

import com.krivosheev.mikhail.unittests.rx.GetItemsListUseCase.DetailedItem
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class GetItemsListUseCaseImpl(private val repository: Repository) : GetItemsListUseCase {

    override fun getItemsWithDetails(): Observable<List<DetailedItem>> {
        return repository.getItems()
            .subscribeOn(Schedulers.io())
            .flattenAsObservable {
                it
            }
            .flatMapSingle { item ->
                repository.getItemDetails(item.id)
                    .map { details ->
                        DetailedItem(item.id, details.name, details.type)
                    }
            }
            .toList()
            .toObservable()
    }
}