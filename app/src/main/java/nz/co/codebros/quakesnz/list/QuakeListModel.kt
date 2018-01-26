package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.BaseModel

/**
 * Created by Leandro on 27/01/2018.
 */
object QuakeListModel : BaseModel<QuakeListState, QuakeListEvent>() {
    override val reducer: (state: QuakeListState, event: QuakeListEvent) -> QuakeListState
        get() = { state, event ->
            when (event) {
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
}