package ar.soflete.cycler

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 27/01/2018.
 */
abstract class BaseModel<S, E>(
        initialState: S,
        reducer: (state: S, event: E) -> S
) : Model<S, E> {
    private val _events: Subject<E> = PublishSubject.create()

    override val events: Observable<E> = _events
    override val state: Observable<S> = _events.scan(initialState, reducer)

    override fun publish(event: E) {
        _events.onNext(event)
    }

    override fun publish(
            eventsObservable: Observable<E>
    ): Disposable = eventsObservable.subscribe { _events.onNext(it) }
}