package nz.co.codebros.quakesnz.list.model

import nz.co.vilemob.rxmodel.Reducer

object QuakeListReducer : Reducer<QuakeListState, QuakeListEvent> {
    override fun apply(state: QuakeListState, event: QuakeListEvent) = when (event) {
        QuakeListEvent.LoadQuakes -> state.copy(isLoading = true)
        is QuakeListEvent.LoadQuakesError -> state.copy(isLoading = false)
        is QuakeListEvent.QuakesLoaded -> state.run {
            copy(isLoading = false, features = event.features, selectedFeature = when {
                event.features.contains(selectedFeature) -> selectedFeature
                else -> null
            })
        }
        is QuakeListEvent.SelectQuake -> state.copy(selectedFeature = event.feature)
        else -> state
    }
}
