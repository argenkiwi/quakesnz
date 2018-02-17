package ar.soflete.cycler

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 10/02/2018.
 */
open class StateEventModel<S, E>(
        initialState: S,
        reducer: Reducer<S, E>
) : EventModel<E>(), StateModel<S> {
    private val stateSubject: Subject<S> = BehaviorSubject.create()
    override val stateObservable: Observable<S> = stateSubject

    init {
        eventObservable.scan(initialState, reducer).subscribe(stateSubject)
    }
}