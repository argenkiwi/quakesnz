package nz.co.codebros.quakesnz.map

import nz.co.codebros.quakesnz.core.data.Coordinates

/**
 * Created by Leandro on 27/11/2017.
 */
object QuakeMapReducer {
    fun reduce(state: QuakeMap.State, coordinates: Coordinates) = state.copy(coordinates = coordinates)
}