package nz.co.codebros.quakesnz.list

import nz.co.codebros.quakesnz.model.Feature

/**
 * Created by leandro on 9/07/15.
 */
interface QuakeListView {
    fun hideProgress()

    fun listQuakes(features: List<Feature>)

    fun showError()

    fun showProgress()

    fun selectFeature(feature: Feature)
}
