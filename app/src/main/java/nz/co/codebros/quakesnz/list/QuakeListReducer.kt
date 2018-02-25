package nz.co.codebros.quakesnz.list

import nz.co.vilemob.rxmodel.Reducer

/**
 * Created by Leandro on 23/02/2018.
 */
object QuakeListReducer : Reducer<QuakeListState, QuakeListEvent> {
    override fun apply(state: QuakeListState, event: QuakeListEvent) = when (event) {
        is QuakeListEvent.LoadQuakes -> state.copy(isLoading = true)
        is QuakeListEvent.LoadQuakesError -> state.copy(isLoading = false)
        is QuakeListEvent.QuakesLoaded -> state.run {
            copy(isLoading = false, features = event.quakes, selectedFeature = when {
                event.quakes.contains(selectedFeature) -> selectedFeature
                else -> null
            })
        }
        is QuakeListEvent.SelectQuake -> state.copy(selectedFeature = event.quake)
    }
}