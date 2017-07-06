package nz.co.codebros.quakesnz.interactor

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * Created by leandro on 10/06/17.
 */
interface LoadFeatureInteractor {
    fun execute(publicId: String, onComplete: Action, onError: Consumer<Throwable>): Disposable?
}
