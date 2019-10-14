package com.krivosheev.mikhail.unittests

interface Repository {
    fun getResultFromCacheOrCreate(id: Int): Backend.Result
}