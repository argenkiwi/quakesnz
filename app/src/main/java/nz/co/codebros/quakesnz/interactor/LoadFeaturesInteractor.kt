package nz.co.codebros.quakesnz.interactor

import io.reactivex.Completable

/**
 * Created by leandro on 10/06/17.
 */
interface LoadFeaturesInteractor {
    fun execute(): Completable
}
