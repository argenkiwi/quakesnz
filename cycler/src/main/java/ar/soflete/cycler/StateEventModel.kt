package ar.soflete.cycler

import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 10/02/2018.
 */
abstract class StateEventModel<S, E>(
        initialState: S,
        reducer: Reducer<S, E>
) : EventModel<E>() {
    private val stateSubject: Subject<S> = BehaviorSubject.create()
    val liveState = LiveDataReactiveStreams.fromPublisher(stateSubject.toFlowable(BackpressureStrategy.LATEST))

    init {
        eventObservable.scan(initialState, reducer).subscribe(stateSubject)
    }
}