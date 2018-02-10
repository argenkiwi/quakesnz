package ar.soflete.cycler

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Leandro on 10/02/2018.
 */
abstract class StateEventModel<S, E>(
        initialState: S,
        reducer: Reducer<S, E>
) : EventModel<E>(), Disposable {
    private val stateObservable = eventObservable.scan(initialState, reducer)
    private val mutableLiveState = MutableLiveData<S>()
    protected val disposables = CompositeDisposable()

    val liveState: LiveData<S> = mutableLiveState

    init {
        disposables.add(stateObservable.subscribe { mutableLiveState.value = it })
    }

    override fun dispose() {
        disposables.dispose()
    }

    override fun isDisposed() = disposables.isDisposed
}