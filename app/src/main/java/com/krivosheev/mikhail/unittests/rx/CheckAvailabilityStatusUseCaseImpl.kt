package com.krivosheev.mikhail.unittests.rx

import com.krivosheev.mikhail.unittests.rx.CheckAvailabilityStatusUseCase.AvailabilityCheckState
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

private const val ERROR_DISPLAY_TIME_SECONDS = 2L

class CheckAvailabilityStatusUseCaseImpl(private val repository: Repository) :
    CheckAvailabilityStatusUseCase {

    override fun checkAvailability(id: Int): Observable<AvailabilityCheckState> =
        repository
            .getItemAvailabilityStatus(id)
            .map {
                if (it) Availability.Available else Availability.NotAvailable
            }
            .onErrorReturn { _ ->
                Availability.Error
            }
            .flatMapObservable { availability ->
                when (availability) {
                    Availability.Available -> Observable.just(
                        AvailabilityCheckState.Success(
                            true
                        )
                    )
                    Availability.NotAvailable -> Observable.just(
                        AvailabilityCheckState.Success(
                            false
                        )
                    )
                    Availability.Error -> Observable
                        .timer(ERROR_DISPLAY_TIME_SECONDS, TimeUnit.SECONDS)
                        .map<AvailabilityCheckState> { _ ->
                            AvailabilityCheckState.Idle
                        }
                        .startWith(AvailabilityCheckState.Error)
                }
            }
            .startWith(AvailabilityCheckState.InProgress)

    private enum class Availability {
        Available,
        NotAvailable,
        Error
    }
}