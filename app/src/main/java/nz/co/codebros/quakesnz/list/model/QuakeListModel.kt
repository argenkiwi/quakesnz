package nz.co.codebros.quakesnz.list.model

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.core.usecase.LoadFeaturesUseCase
import nz.co.codebros.quakesnz.scope.ActivityScope
import nz.co.codebros.quakesnz.util.BaseModel
import nz.co.codebros.quakesnz.util.changes
import javax.inject.Inject

@ActivityScope
class QuakeListModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val loadFeaturesUseCase: LoadFeaturesUseCase,
    private val tracker: FirebaseAnalytics
) : BaseModel<QuakeListState, QuakeListEvent>(
    QuakeListState(false, null, null),
    QuakeListReducer
) {
    override fun subscribe() = CompositeDisposable(
        publish(
            eventObservable
                .startWith(QuakeListEvent.LoadQuakes())
                .ofType<QuakeListEvent.LoadQuakes>()
                .flatMap {
                    loadFeaturesUseCase.execute()
                        .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                        .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                        .observeOn(AndroidSchedulers.mainThread())
                },
            sharedPreferences.changes()
                .filter { it == "pref_intensity" }
                .map { QuakeListEvent.RefreshQuakes }
        ),
        eventObservable.subscribe {
            when (it) {
                QuakeListEvent.RefreshQuakes -> tracker.logEvent("refresh", Bundle.EMPTY)
                is QuakeListEvent.SelectQuake -> tracker.logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "quake",
                        FirebaseAnalytics.Param.ITEM_ID to it.feature.properties.publicID
                    )
                )
            }
        }
    )
}
