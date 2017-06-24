package nz.co.codebros.quakesnz.repository

import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.model.Feature

/**
 * Created by leandro on 19/06/17.
 */

class FeatureRepository(
        subject: Subject<Feature>
) : BaseBundleRepository<Feature>(subject) {
    override fun getKey(): String {
        return Feature::class.java.canonicalName
    }
}
