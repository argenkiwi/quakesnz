package nz.co.codebros.quakesnz.repository

import io.reactivex.Completable
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 18/06/17.
 */

class FeatureCollectionRepository(
        observable: Subject<FeatureCollection>,
        private val service: GeonetService
) : Repository<FeatureCollection>(observable) {
    fun load(mmi: Int): Completable {
        return load(service.getQuakes(mmi))
    }
}
