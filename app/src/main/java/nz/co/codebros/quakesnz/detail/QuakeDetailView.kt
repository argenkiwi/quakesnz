package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.model.Feature

/**
 * Created by leandro on 7/07/16.
 */
internal interface QuakeDetailView {
    fun share(publicId: Feature)

    fun showDetails(feature: Feature)

    fun showLoadingError()
}
