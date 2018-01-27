package nz.co.codebros.quakesnz.list

import io.reactivex.functions.BiFunction

/**
 * Created by Leandro on 28/01/2018.
 */
class QuakeListReducer : BiFunction<QuakeListState, QuakeListEvent, QuakeListState> {
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