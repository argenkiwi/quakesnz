package ar.soflete.cycler

import android.arch.lifecycle.LiveData
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 27/01/2018.
 */
interface Model<S, E> {
    val state: LiveData<S>
    val events: Subject<E>
    val reducer: (state: S, event: E) -> S
    fun init(initialState: S): Disposable
}