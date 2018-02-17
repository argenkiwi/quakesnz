package ar.soflete.cycler

import io.reactivex.disposables.Disposable

/**
 * Created by Leandro on 13/02/2018.
 */
abstract class DisposableStateEventModel<S, E>(
        initialState: S,
        reducer: Reducer<S, E>
) : StateEventModel<S, E>(initialState, reducer), Disposable {
    protected abstract val disposable: Disposable
    override fun isDisposed() = disposable.isDisposed
    override fun dispose() {
        disposable.dispose()
    }
}