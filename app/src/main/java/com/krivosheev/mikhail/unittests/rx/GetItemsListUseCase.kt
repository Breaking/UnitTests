package com.krivosheev.mikhail.unittests.rx

import io.reactivex.Observable


interface GetItemsListUseCase {

    fun getItemsWithDetails(): Observable<List<DetailedItem>>

    data class DetailedItem(val id: Int, val name: String, val type: String)
}