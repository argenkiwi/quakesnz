package nz.co.codebros.quakesnz.interactor

import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.Feature
import nz.co.codebros.quakesnz.model.FeatureCollection
import nz.co.codebros.quakesnz.repository.Publisher

/**
 * Created by leandro on 18/06/17.
 */

class LoadFeatureInteractorImpl(
        private val featureCollectionObservable: Observable<FeatureCollection>,
        private val geonetService: GeonetService, private val featurePublisher: Publisher<Feature>
) : LoadFeatureInteractor {

    override fun execute(completableObserver: CompletableObserver, publicID: String) {
        // FIXME Use Observable<Feature[]> instead.
        featureCollectionObservable
                .map { featureCollection -> featureCollection.features }
                .flatMap { features -> Observable.fromArray(*features) }
                .filter { feature -> feature.properties.publicId == publicID }
                .firstElement()
                .concatWith(geonetService.getQuake(publicID)
                        .map { featureCollection -> featureCollection.features[0] })
                .firstElement()
                .toSingle()
                .doAfterSuccess { feature -> featurePublisher.publish(feature) }
                .toCompletable()
                .subscribe(completableObserver)
    }
}
