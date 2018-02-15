package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 16/02/2018.
 */
interface QuakeDetailView {
    fun updateFeature(feature: Feature)
    fun showLoadingError()
}