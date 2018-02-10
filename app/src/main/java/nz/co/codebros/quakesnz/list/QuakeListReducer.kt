package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.Reducer

/**
 * Created by Leandro on 28/01/2018.
 */
class QuakeListReducer : Reducer<QuakeListState, QuakeListEvent> {
    override fun apply(state: QuakeListState, event: QuakeListEvent) = when (event) {
        is QuakeListEvent.LoadQuakes -> {
            state.copy(isLoading = true)
        }
        is QuakeListEvent.LoadQuakesError -> {
            state.copy(isLoading = false, error = event.error)
        }
        is QuakeListEvent.QuakesLoaded -> {
            state.copy(isLoading = false, features = event.quakes)
        }
        is QuakeListEvent.SelectQuake -> {
            state.copy(selectedFeature = event.quake)
        }
    }
}