package nz.co.codebros.quakesnz.repository

import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.BaseBundleRepository
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by leandro on 19/06/17.
 */

class FeatureRepository @Inject constructor(
        subject: Subject<Feature>
) : BaseBundleRepository<Feature>(subject) {
    override fun getKey(): String = Feature::class.java.canonicalName
}
