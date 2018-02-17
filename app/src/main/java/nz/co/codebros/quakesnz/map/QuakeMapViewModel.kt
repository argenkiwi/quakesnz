package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.LiveStateModel
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeMapViewModel @Inject constructor(
        private val quakeMapModel: QuakeMapModel
) : ViewModel() {

    val quakeMapState = LiveStateModel(quakeMapModel)

    override fun onCleared() {
        super.onCleared()
        quakeMapModel.dispose()
    }
}