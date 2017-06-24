package nz.co.codebros.quakesnz.presenter

import java.util.ArrayList

import io.reactivex.disposables.Disposable

/**
 * Created by leandro on 19/06/17.
 */

abstract class BasePresenter : Presenter {
    private val disposables = ArrayList<Disposable>()

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected fun disposeAll() {
        while (!disposables.isEmpty()) {
            disposables.removeAt(0).dispose()
        }
    }
}
