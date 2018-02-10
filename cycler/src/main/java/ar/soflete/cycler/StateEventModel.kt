package ar.soflete.cycler

import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 10/02/2018.
 */
abstract class StateEventModel<S, E>(
        initialState: S,
        reducer: Reducer<S, E>
) : EventModel<E>(), Disposable {
    private val stateSubject: Subject<S> = BehaviorSubject.create()
    protected val disposables = CompositeDisposable()
    val liveState = LiveDataReactiveStreams.fromPublisher(stateSubject.toFlowable(BackpressureStrategy.LATEST))

    init {
        eventObservable.scan(initialState, reducer).subscribe(stateSubject)
    }

    override fun isDisposed() = disposables.isDisposed

    override fun dispose() {
        disposables.dispose()
    }
}