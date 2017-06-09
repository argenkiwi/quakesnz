package nz.co.codebros.quakesnz.interactor

import io.reactivex.SingleObserver
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 10/06/17.
 */
internal interface GetFeaturesInteractor {
    fun execute(subscriber: SingleObserver<FeatureCollection>)
}
