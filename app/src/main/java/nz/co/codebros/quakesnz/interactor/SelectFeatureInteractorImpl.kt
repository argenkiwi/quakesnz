package nz.co.codebros.quakesnz.interactor

import io.reactivex.Observer
import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by leandro on 19/06/17.
 */

class SelectFeatureInteractorImpl(
        private val featureObserver: Observer<Feature>
) : SelectFeatureInteractor {
    override fun execute(feature: Feature) {
        featureObserver.onNext(feature)
    }
}
