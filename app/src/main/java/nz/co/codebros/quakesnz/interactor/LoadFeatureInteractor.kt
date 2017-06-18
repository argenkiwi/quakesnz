package nz.co.codebros.quakesnz.interactor

import io.reactivex.CompletableObserver

/**
 * Created by leandro on 10/06/17.
 */
internal interface LoadFeatureInteractor {
    fun execute(completableObserver: CompletableObserver, publicID: String)
}
