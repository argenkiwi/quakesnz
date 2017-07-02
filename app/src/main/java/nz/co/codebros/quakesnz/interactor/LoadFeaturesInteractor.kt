package nz.co.codebros.quakesnz.interactor

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * Created by leandro on 10/06/17.
 */
internal interface LoadFeaturesInteractor {
    fun execute(onSuccess: Action, onError: Consumer<Throwable>): Disposable?
    fun execute()
}
