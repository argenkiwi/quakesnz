package nz.co.codebros.quakesnz.repository

import io.reactivex.Completable
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 18/06/17.
 */

class FeatureCollectionRepository(
        publisher: Publisher<FeatureCollection>,
        private val service: GeonetService
) : Repository<FeatureCollection>(publisher) {
    fun load(mmi: Int): Completable {
        return load(service.getQuakes(mmi))
    }
}
