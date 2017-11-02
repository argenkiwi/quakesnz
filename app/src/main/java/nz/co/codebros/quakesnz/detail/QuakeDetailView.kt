package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.model.Feature

/**
 * Created by leandro on 7/07/16.
 */
interface QuakeDetailView {
    fun share(feature: Feature)

    fun showDetails(feature: Feature)

    fun showLoadingError()
}
