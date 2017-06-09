package nz.co.codebros.quakesnz.interactor

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.Feature

/**
 * Created by leandro on 7/07/16.
 */
class GetFeatureInteractorImpl(private val service: GeonetService) : GetFeatureInteractor {
    override fun execute(observer: SingleObserver<Feature>, publicID: String) {
        service.getQuake(publicID)
                .map { it.features[0] }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}
