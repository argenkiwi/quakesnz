package ar.soflete.cycler

import io.reactivex.Observable

/**
 * Created by Leandro on 18/02/2018.
 */
interface StateModel<S> {
    val stateObservable: Observable<S>
}