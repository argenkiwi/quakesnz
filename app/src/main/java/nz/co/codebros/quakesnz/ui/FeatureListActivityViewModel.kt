package nz.co.codebros.quakesnz.ui

import ar.soflete.daggerlifecycle.DaggerViewModel
import nz.co.codebros.quakesnz.LiveEventModel
import nz.co.codebros.quakesnz.list.QuakeListModel
import javax.inject.Inject

/**
 * Created by Leandro on 23/11/2017.
 */
class FeatureListActivityViewModel @Inject constructor(
        quakeListModel: QuakeListModel
) : DaggerViewModel() {
    val quakeListEvents = LiveEventModel(quakeListModel)
}