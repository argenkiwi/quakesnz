package nz.co.codebros.quakesnz.util

import io.reactivex.BackpressureStrategy
import nz.co.vilemob.rxmodel.Reducer
import nz.co.vilemob.rxmodel.StateEventModel

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
}