package nz.co.codebros.quakesnz.map.model

import nz.co.vilemob.rxmodel.Reducer

object QuakeMapReducer : Reducer<QuakeMapState, QuakeMapEvent> {
    override fun apply(state: QuakeMapState, event: QuakeMapEvent) = when (event) {
        is QuakeMapEvent.OnNewCoordinates -> state.copy(coordinates = event.coordinates)
    }
}
