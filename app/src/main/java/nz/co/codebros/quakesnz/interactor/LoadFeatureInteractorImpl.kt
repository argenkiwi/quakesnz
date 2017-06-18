package nz.co.codebros.quakesnz.interactor

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.Feature
import nz.co.codebros.quakesnz.model.FeatureCollection
import nz.co.codebros.quakesnz.repository.Publisher

/**
 * Created by leandro on 18/06/17.
 */

class LoadFeatureInteractorImpl(
        private val featureCollectionObservable: Observable<FeatureCollection>,
        private val geonetService: GeonetService,
        private val featurePublisher: Publisher<Feature>
) : LoadFeatureInteractor {

    override fun execute(completableObserver: CompletableObserver, publicID: String) {
        // FIXME Only call service if feature hasn't been loaded yet.
//        featureCollectionObservable
        geonetService.getQuake(publicID)
                .map { featureCollection -> featureCollection.features }
                .flatMap { features -> Observable.fromArray(*features) }
//                .filter { feature -> feature.properties.publicId == publicID }
                .firstElement()
                .toSingle()
                .doAfterSuccess { feature -> featurePublisher.publish(feature) }
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver)
    }
}
