package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.presenter.View

/**
 * Created by leandro on 7/07/16.
 */
interface QuakeDetailView : View {
    fun share(publicId: Feature)

    fun showDetails(feature: Feature)

    fun showLoadingError()
}
