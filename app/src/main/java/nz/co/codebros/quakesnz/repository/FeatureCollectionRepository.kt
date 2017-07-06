package nz.co.codebros.quakesnz.repository

import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.BaseBundleRepository
import nz.co.codebros.quakesnz.core.model.FeatureCollection

/**
 * Created by leandro on 18/06/17.
 */

class FeatureCollectionRepository(
        subject: Subject<FeatureCollection>
) : BaseBundleRepository<FeatureCollection>(subject) {
    override fun getKey():String {
        return  FeatureCollection::class.java.canonicalName
    }
}
