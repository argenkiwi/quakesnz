package nz.co.codebros.quakesnz.repository

import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.BaseBundleRepository
import nz.co.codebros.quakesnz.core.model.FeatureCollection
import javax.inject.Inject

/**
 * Created by leandro on 18/06/17.
 */

class FeatureCollectionRepository @Inject constructor(
        subject: Subject<FeatureCollection>
) : BaseBundleRepository<FeatureCollection>(subject) {
    override fun getKey(): String = FeatureCollection::class.java.canonicalName
}
