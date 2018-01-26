package ar.soflete.cycler

import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 27/01/2018.
 */
abstract class BaseModel<S, E> : Model<S, E> {
    override val state = MutableLiveData<S>()
    override val events: Subject<E> = PublishSubject.create()
    override fun init(initialState: S): Disposable = events
            .scan(initialState, reducer)
            .subscribe { state.value = it }
}