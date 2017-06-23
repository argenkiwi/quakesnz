package nz.co.codebros.quakesnz.repository

import io.reactivex.Completable
import io.reactivex.Observer
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 18/06/17.
 */

class FeatureCollectionRepository(
        observer: Observer<FeatureCollection>,
        private val service: GeonetService
) : Repository<FeatureCollection>(observer) {
    fun load(mmi: Int): Completable {
        return load(service.getQuakes(mmi))
    }
}
