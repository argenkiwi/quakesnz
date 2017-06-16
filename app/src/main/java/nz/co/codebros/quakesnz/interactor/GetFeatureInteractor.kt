package nz.co.codebros.quakesnz.interactor

import io.reactivex.SingleObserver
import nz.co.codebros.quakesnz.model.Feature

/**
 * Created by leandro on 10/06/17.
 */
internal interface GetFeatureInteractor {
    fun execute(observer: SingleObserver<Feature>, publicID: String)
}