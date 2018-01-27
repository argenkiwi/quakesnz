package ar.soflete.cycler

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by Leandro on 27/01/2018.
 */
interface Model<S, E> {
    val state: Observable<S>
    val events: Observable<E>
    fun publish(event: E)
    fun publish(eventsObservable: Observable<E>): Disposable
}