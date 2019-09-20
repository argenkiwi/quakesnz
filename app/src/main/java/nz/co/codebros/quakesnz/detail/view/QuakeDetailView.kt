package nz.co.codebros.quakesnz.detail.view

import nz.co.codebros.quakesnz.core.data.Feature

interface QuakeDetailView {
    fun updateFeature(feature: Feature)
    fun showLoadingError()
}
