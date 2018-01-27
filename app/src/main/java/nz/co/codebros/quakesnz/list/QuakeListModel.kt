package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.BaseModel

/**
 * Created by Leandro on 27/01/2018.
 */
class QuakeListModel : BaseModel<QuakeListState, QuakeListEvent>(
        QuakeListState(false),
        { state, event ->
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
)