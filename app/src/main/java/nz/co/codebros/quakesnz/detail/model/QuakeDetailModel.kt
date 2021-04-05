package nz.co.codebros.quakesnz.detail.model

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import nz.co.codebros.quakesnz.map.model.QuakeMapEvent
import nz.co.codebros.quakesnz.map.model.QuakeMapModel
import nz.co.codebros.quakesnz.util.BaseModel
import nz.co.codebros.quakesnz.util.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuakeDetailModel @Inject constructor(
        private val reactor: QuakeDetailReactor,
        private val quakeMapModel: QuakeMapModel,
) : BaseModel<QuakeDetailState, QuakeDetailEvent>(
        QuakeDetailState(false, null), QuakeDetailReducer
) {

    override fun subscribe(): Disposable = CompositeDisposable(
            publish(eventObservable.mapNotNull(reactor::react).flatMapSingle { it }),
            stateObservable
                    .mapNotNull { it.feature?.geometry?.coordinates }
                    .distinct()
                    .subscribe({
                        quakeMapModel.publish(QuakeMapEvent.OnNewCoordinates(it))
                    }, {
                        Log.e(QuakeDetailModel::class.simpleName, "Coordinates are null.", it)
                    })
    )
}
