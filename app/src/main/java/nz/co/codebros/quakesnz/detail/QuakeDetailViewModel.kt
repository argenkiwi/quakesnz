package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.util.toLiveData
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeDetailViewModel @Inject constructor(
        private val quakeDetailModel: QuakeDetailModel
) : ViewModel() {

    val quakeDetailState = quakeDetailModel.stateObservable.toLiveData(BackpressureStrategy.LATEST)
    val quakeDetailEvents
        get() = quakeDetailModel.eventObservable.toLiveData(BackpressureStrategy.LATEST)

    override fun onCleared() {
        super.onCleared()
        quakeDetailModel.dispose()
    }
}