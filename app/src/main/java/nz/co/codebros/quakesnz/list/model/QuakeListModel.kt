package nz.co.codebros.quakesnz.list.model

import android.content.SharedPreferences
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.map.model.QuakeMapEvent
import nz.co.codebros.quakesnz.map.model.QuakeMapModel
import nz.co.codebros.quakesnz.util.BaseModel
import nz.co.codebros.quakesnz.util.changes
import nz.co.codebros.quakesnz.util.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuakeListModel @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val reactor: QuakeListReactor,
        private val quakeMapModel: QuakeMapModel,
) : BaseModel<QuakeListState, QuakeListEvent>(
        QuakeListState(false, null, null),
        QuakeListReducer
) {
    override fun subscribe() = CompositeDisposable(
            publish(
                    eventObservable.startWith(QuakeListEvent.LoadQuakes)
                            .mapNotNull(reactor::react)
                            .flatMapSingle { it },
                    sharedPreferences.changes()
                            .filter { it == "pref_intensity" }
                            .map { QuakeListEvent.RefreshQuakes }
            ),
            stateObservable
                    .mapNotNull { it.selectedFeature?.geometry?.coordinates }
                    .distinct()
                    .subscribe({
                        quakeMapModel.publish(QuakeMapEvent.OnNewCoordinates(it))
                    }, {
                        Log.e(QuakeListModel::class.simpleName, "Coordinates are null.", it)
                    })
    )
}
