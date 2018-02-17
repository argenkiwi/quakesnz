package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModel
import nz.co.codebros.quakesnz.LiveEventModel
import nz.co.codebros.quakesnz.LiveStateModel
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeDetailViewModel @Inject constructor(
        private val quakeDetailModel: QuakeDetailModel
) : ViewModel() {

    val quakeDetailState = LiveStateModel(quakeDetailModel)
    val quakeDetailEvents = LiveEventModel(quakeDetailModel)

    override fun onCleared() {
        super.onCleared()
        quakeDetailModel.dispose()
    }
}