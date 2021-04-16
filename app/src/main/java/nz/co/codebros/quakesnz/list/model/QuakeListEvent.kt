package nz.co.codebros.quakesnz.list.model

import nz.co.codebros.quakesnz.core.data.Feature

sealed class QuakeListEvent {
    object LoadQuakes : QuakeListEvent()
    object RefreshQuakes : QuakeListEvent()
    data class QuakesLoaded(val features: List<Feature>) : QuakeListEvent()
    data class SelectQuake(val feature: Feature) : QuakeListEvent()
    data class LoadQuakesError(val error: Throwable) : QuakeListEvent()
}
