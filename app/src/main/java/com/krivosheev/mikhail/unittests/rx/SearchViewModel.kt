package com.krivosheev.mikhail.unittests.rx

import androidx.lifecycle.MutableLiveData
import com.krivosheev.mikhail.unittests.rx.GetItemsListUseCase


interface SearchViewModel {

    val items: MutableLiveData<List<GetItemsListUseCase.DetailedItem>>

    fun search(query: String)

    fun onDestroy()
}