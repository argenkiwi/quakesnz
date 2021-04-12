package nz.co.codebros.quakesnz.list.model

fun reduce(state: QuakeListState, event: QuakeListEvent) = when (event) {
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
