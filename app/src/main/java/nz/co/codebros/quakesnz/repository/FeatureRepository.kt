package nz.co.codebros.quakesnz.repository

import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.Feature
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 19/06/17.
 */

class FeatureRepository(
        observer: Observer<Feature>,
        private val service: GeonetService
) : Repository<Feature>(observer) {
    fun load(publicId: String): Completable {
        return load(service.getQuake(publicId)
                .map { featureCollection -> featureCollection.features }
                .map { features -> features[0] })
    }
}
