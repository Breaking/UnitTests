package com.krivosheev.mikhail.unittests.rx

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable


class SearchViewModelImpl(private val filteredItemsRepository: FilteredItemsRepository) :
    SearchViewModel {

    override val items: MutableLiveData<List<GetItemsListUseCase.DetailedItem>> = MutableLiveData()

    private val disposables = CompositeDisposable()

    init {
        filteredItemsRepository.getFilteredItems().subscribe {
            items.postValue(it)
        }.also {
            disposables.add(it)
        }
    }

    override fun search(query: String) {
        filteredItemsRepository.search(query)
    }

    override fun onDestroy() {
        disposables.clear()
    }
}