package nz.co.codebros.quakesnz.core

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by leandro on 19/06/17.
 */

abstract class BasePresenter<out V, in P>(val view: V) : Presenter<P> {
    private val disposables = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    private fun disposeAll() {
        disposables.dispose()
    }

    override fun onInit(props: P?) {

    }

    override fun onViewCreated() {

    }

    override fun onDestroyView() {
        disposeAll()
    }

    open fun onRestoreState(bundle: Bundle) {

    }

    open fun onSaveState(bundle: Bundle) {

    }
}
