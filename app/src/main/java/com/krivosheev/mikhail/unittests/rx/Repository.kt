package com.krivosheev.mikhail.unittests.rx

import io.reactivex.Single


interface Repository {

    fun getItems(): Single<List<Item>>

    fun getItemDetails(id: Int): Single<ItemDetails>

    fun getItemAvailabilityStatus(id: Int): Single<Boolean>

    data class Item(val id: Int)

    data class ItemDetails(val name: String, val type: String)
}