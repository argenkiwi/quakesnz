package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListModel
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel @Inject constructor(
        val quakeListModel: QuakeListModel
) : ViewModel() {

    private val disposable = quakeListModel.subscribe()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun refreshQuakes() {
        quakeListModel.publish(QuakeListEvent.RefreshQuakes)
    }

    fun selectQuake(feature: Feature) {
        quakeListModel.publish(QuakeListEvent.SelectQuake(feature))
    }
}
