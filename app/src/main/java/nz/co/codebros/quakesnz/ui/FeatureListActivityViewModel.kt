package nz.co.codebros.quakesnz.ui

import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.list.QuakeListModel
import nz.co.codebros.quakesnz.util.toLiveData
import nz.co.vilemob.daggerviewmodel.DaggerViewModel
import javax.inject.Inject

/**
 * Created by Leandro on 23/11/2017.
 */
class FeatureListActivityViewModel @Inject constructor(
        private val quakeListModel: QuakeListModel
) : DaggerViewModel() {
    val quakeListEvents
        get() = quakeListModel.eventObservable.toLiveData(BackpressureStrategy.LATEST)
}