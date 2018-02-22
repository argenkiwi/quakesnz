package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeDetailViewModel @Inject constructor(
        val quakeDetailModel: QuakeDetailModel
) : ViewModel() {
    private val disposable = quakeDetailModel.subscribe()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}