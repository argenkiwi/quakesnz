package ar.soflete.cycler

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 10/02/2018.
 */
open class EventModel<E> {
    private val eventSubject: Subject<E> = PublishSubject.create()
    val eventObservable: Observable<E> = eventSubject

    fun publish(event: E) {
        eventSubject.onNext(event)
    }

    fun publish(eventObservable: Observable<E>) = eventObservable.subscribe { publish(it) }
}