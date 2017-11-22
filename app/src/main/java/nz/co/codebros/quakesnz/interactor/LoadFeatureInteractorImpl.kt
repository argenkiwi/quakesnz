package nz.co.codebros.quakesnz.interactor

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by leandro on 18/06/17.
 */

class LoadFeatureInteractorImpl @Inject constructor(
        private val service: GeonetService
) : LoadFeatureInteractor {
    override fun execute(publicId: String): Observable<Feature> = service.getQuake(publicId)
            .map { (features) -> features[0] }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
