package nz.co.codebros.quakesnz.list

import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.scope.ActivityScope
import nz.co.codebros.quakesnz.usecase.LoadFeaturesUseCase
import nz.co.codebros.quakesnz.util.BaseModel
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 23/02/2018.
 */
@ActivityScope
class QuakeListModel @Inject constructor(
        private val loadFeaturesUseCase: LoadFeaturesUseCase,
        @Named("app") private val tracker: Tracker
) : BaseModel<QuakeListState, QuakeListEvent>(
        QuakeListState(false, null, null),
        QuakeListReducer
) {
    override fun subscribe() = CompositeDisposable(
            publish(eventObservable
                    .startWith(QuakeListEvent.LoadQuakes())
                    .ofType<QuakeListEvent.LoadQuakes>()
                    .flatMap {
                        loadFeaturesUseCase.execute()
                                .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                                .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                    }),
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
    )
}