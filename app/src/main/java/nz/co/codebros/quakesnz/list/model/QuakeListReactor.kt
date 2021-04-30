package nz.co.codebros.quakesnz.list.model

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.runBlocking
import nz.co.codebros.quakesnz.core.usecase.LoadFeaturesUseCase
import javax.inject.Inject

class QuakeListReactor @Inject constructor(
        private val loadFeaturesUseCase: LoadFeaturesUseCase,
        private val tracker: FirebaseAnalytics,
) {

    fun react(event: QuakeListEvent): Single<QuakeListEvent>? = when (event) {
        is QuakeListEvent.LoadQuakes -> Single.fromCallable { runBlocking { loadFeaturesUseCase.execute() } }
                .map<QuakeListEvent> { QuakeListEvent.QuakesLoaded(it.features) }
                .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                .observeOn(AndroidSchedulers.mainThread())
        is QuakeListEvent.RefreshQuakes -> {
            tracker.logEvent("refresh", Bundle.EMPTY)
            Single.fromCallable { runBlocking { loadFeaturesUseCase.execute(true) } }
                    .map<QuakeListEvent> { QuakeListEvent.QuakesLoaded(it.features) }
                    .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                    .observeOn(AndroidSchedulers.mainThread())
        }
        is QuakeListEvent.LoadQuakesError -> null
        is QuakeListEvent.QuakesLoaded -> null
        is QuakeListEvent.SelectQuake -> with(event) {
            tracker.logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                            FirebaseAnalytics.Param.CONTENT_TYPE to "quake",
                            FirebaseAnalytics.Param.ITEM_ID to feature.properties.publicID
                    )
            )

            null
        }
    }
}
