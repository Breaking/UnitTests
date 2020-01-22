package com.krivosheev.mikhail.unittests.rx

import io.reactivex.Observable


interface CheckAvailabilityStatusUseCase {

    fun checkAvailability(id: Int): Observable<AvailabilityCheckState>

    sealed class AvailabilityCheckState {

        object InProgress : AvailabilityCheckState()

        object Error : AvailabilityCheckState()

        object Idle : AvailabilityCheckState()

        data class Success(val isAvailable: Boolean) : AvailabilityCheckState()
    }
}