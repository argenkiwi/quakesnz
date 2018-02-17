package nz.co.codebros.quakesnz.map

import ar.soflete.cycler.Reducer

/**
 * Created by Leandro on 16/02/2018.
 */
object QuakeMapReducer : Reducer<QuakeMapState, QuakeMapEvent> {
    override fun apply(state: QuakeMapState, event: QuakeMapEvent) = when (event) {
        is QuakeMapEvent.QuakeSelected -> state.copy(coordinates = event.feature.geometry.coordinates)
    }
}