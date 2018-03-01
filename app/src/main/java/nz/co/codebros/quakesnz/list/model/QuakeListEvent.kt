package nz.co.codebros.quakesnz.list.model

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 25/11/2017.
 */
sealed class QuakeListEvent {
    open class LoadQuakes : QuakeListEvent()
    object RefreshQuakes : LoadQuakes()
    data class QuakesLoaded(val features: List<Feature>) : QuakeListEvent()
    data class SelectQuake(val feature: Feature) : QuakeListEvent()
    data class LoadQuakesError(val error: Throwable) : QuakeListEvent()
}
