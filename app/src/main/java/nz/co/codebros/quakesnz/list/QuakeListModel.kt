package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.StateEventModel
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 27/01/2018.
 */
class QuakeListModel @Inject constructor(
        loadFeaturesInteractor: LoadFeaturesInteractor,
        selectFeatureInteractor: SelectFeatureInteractor,
        @Named("app") tracker: Tracker
) : StateEventModel<QuakeListState, QuakeListEvent>(
        QuakeListState(false),
        QuakeListReducer()
) {
    init {
        // Bind side-effects to events
        publish(eventObservable
                .startWith(QuakeListEvent.LoadQuakes())
                .filter { it is QuakeListEvent.LoadQuakes }
                .flatMap {
                    loadFeaturesInteractor.execute()
                            .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                            .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                })

        eventObservable
                .filter { it is QuakeListEvent.SelectQuake }
                .map { (it as QuakeListEvent.SelectQuake).quake }
                .subscribe { selectFeatureInteractor.execute(it) }

        eventObservable.subscribe {
            when (it) {
                QuakeListEvent.RefreshQuakes -> tracker.send(HitBuilders.EventBuilder()
                        .setCategory("Interactions")
                        .setAction("Refresh")
                        .build())
                is QuakeListEvent.SelectQuake -> tracker.send(HitBuilders.EventBuilder()
                        .setCategory("Interactions")
                        .setAction("Select quake")
                        .build())
            }
        }
    }
}