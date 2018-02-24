package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import nz.co.codebros.quakesnz.core.data.Feature
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