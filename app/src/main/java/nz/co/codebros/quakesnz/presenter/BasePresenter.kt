package nz.co.codebros.quakesnz.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by leandro on 19/06/17.
 */

abstract class BasePresenter<out V : View>(
        val view: V
) : Presenter {
    private val disposables = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected fun disposeAll() {
        disposables.dispose()
    }
}
