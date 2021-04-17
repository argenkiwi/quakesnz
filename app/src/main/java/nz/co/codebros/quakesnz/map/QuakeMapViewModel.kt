package nz.co.codebros.quakesnz.map

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.map.model.QuakeMapModel
import nz.co.codebros.quakesnz.util.toLiveData
import javax.inject.Inject

@HiltViewModel
class QuakeMapViewModel @Inject constructor(
        private val quakeMapModel: QuakeMapModel
) : ViewModel() {
    val liveState = quakeMapModel.stateObservable.toLiveData(BackpressureStrategy.LATEST)
}
