package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.Reducer

/**
 * Created by Leandro on 27/11/2017.
 */
object QuakeListReducer : Reducer<QuakeListState, QuakeListEvent> {
    override fun reduce(state: QuakeListState, event: QuakeListEvent) = when (event) {
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