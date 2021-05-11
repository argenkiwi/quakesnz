package nz.co.codebros.quakesnz.list.model

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import nz.co.codebros.quakesnz.core.usecase.LoadFeaturesUseCase
import javax.inject.Inject

class QuakeListReactor @Inject constructor(
    private val loadFeaturesUseCase: LoadFeaturesUseCase,
    private val tracker: FirebaseAnalytics,
) {

    suspend fun react(event: QuakeListEvent): QuakeListEvent? = when (event) {
        is QuakeListEvent.LoadQuakes -> try {
            val result = loadFeaturesUseCase.execute()
            QuakeListEvent.QuakesLoaded(result.features)
        } catch (t: Throwable) {
            QuakeListEvent.LoadQuakesError(t)
        }
        is QuakeListEvent.RefreshQuakes -> try {
            tracker.logEvent("refresh", Bundle.EMPTY)
            val result = loadFeaturesUseCase.execute(true)
            QuakeListEvent.QuakesLoaded(result.features)
        } catch (t: Throwable) {
            QuakeListEvent.LoadQuakesError(t)
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
