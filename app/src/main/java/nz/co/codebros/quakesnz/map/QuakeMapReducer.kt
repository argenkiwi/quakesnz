package nz.co.codebros.quakesnz.map

import ar.soflete.cycler.Reducer
import nz.co.codebros.quakesnz.core.data.Coordinates

/**
 * Created by Leandro on 27/11/2017.
 */
object QuakeMapReducer : Reducer<QuakeMap.State, Coordinates> {
    override fun reduce(state: QuakeMap.State, coordinates: Coordinates) =
            state.copy(coordinates = coordinates)
}