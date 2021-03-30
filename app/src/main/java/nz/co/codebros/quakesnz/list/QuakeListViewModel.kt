package nz.co.codebros.quakesnz.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListModel
import javax.inject.Inject

@HiltViewModel
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
