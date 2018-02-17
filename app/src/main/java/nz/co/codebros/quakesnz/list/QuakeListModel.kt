package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.DisposableStateEventModel
import ar.soflete.cycler.Reducer
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.scope.ActivityScope
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 12/02/2018.
 */
@ActivityScope
class QuakeListModel @Inject constructor(
        loadFeaturesInteractor: LoadFeaturesInteractor,
        @Named("app") tracker: Tracker
) : DisposableStateEventModel<QuakeListState, QuakeListEvent>(QuakeListState(false), Companion) {
    override val disposable = publish(eventObservable
            .startWith(QuakeListEvent.LoadQuakes())
            .ofType<QuakeListEvent.LoadQuakes>()
            .flatMap {
                loadFeaturesInteractor.execute()
                        .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                        .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
            }
            .mergeWith {
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
            })

    companion object : Reducer<QuakeListState, QuakeListEvent> {
        override fun apply(state: QuakeListState, event: QuakeListEvent) = when (event) {
            is QuakeListEvent.LoadQuakes -> state.copy(isLoading = true)
            is QuakeListEvent.LoadQuakesError -> state.copy(isLoading = false)
            is QuakeListEvent.QuakesLoaded -> state.copy(isLoading = false, features = event.quakes)
            is QuakeListEvent.SelectQuake -> state.copy(selectedFeature = event.quake)
        }
    }
}