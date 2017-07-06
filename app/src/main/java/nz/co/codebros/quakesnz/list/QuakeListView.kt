package nz.co.codebros.quakesnz.list

import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.presenter.View

/**
 * Created by leandro on 9/07/15.
 */
interface QuakeListView : View{
    fun hideProgress()

    fun listQuakes(features: List<Feature>)

    fun showError()

    fun showProgress()

    fun selectFeature(feature: Feature)
}
