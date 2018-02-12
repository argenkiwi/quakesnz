package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.Reducer
import ar.soflete.cycler.StateEventModel

/**
 * Created by Leandro on 12/02/2018.
 */
class QuakeListModel(
        initialState: QuakeListState
) : StateEventModel<QuakeListState, QuakeListEvent>(initialState, Companion) {
    companion object : Reducer<QuakeListState, QuakeListEvent> {
        override fun apply(state: QuakeListState, event: QuakeListEvent) = when (event) {
            is QuakeListEvent.LoadQuakes -> state.copy(isLoading = true)
            is QuakeListEvent.LoadQuakesError -> state.copy(isLoading = false)
            is QuakeListEvent.QuakesLoaded -> state.copy(isLoading = false, features = event.quakes)
            is QuakeListEvent.SelectQuake -> state.copy(selectedFeature = event.quake)
        }
    }
}