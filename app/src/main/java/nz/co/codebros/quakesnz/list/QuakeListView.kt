package nz.co.codebros.quakesnz.list

import nz.co.codebros.quakesnz.core.model.Feature

/**
 * Created by leandro on 9/07/15.
 */
interface QuakeListView {
    fun listQuakes(features: List<Feature>)

    fun selectFeature(feature: Feature)
}
