package nz.co.codebros.quakesnz.util

import ar.soflete.cycler.Reducer
import ar.soflete.cycler.StateEventModel
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.Disposables

/**
 * Created by Leandro on 23/02/2018.
 */
abstract class BaseModel<S, E>(
        initialState: S,
        reducer: Reducer<S, E>
) : StateEventModel<S, E>(initialState, reducer) {
    val state = stateObservable.toLiveData(BackpressureStrategy.LATEST)
    val events
        get() = eventObservable.toLiveData(BackpressureStrategy.LATEST)

    open fun subscribe() = Disposables.empty()
}