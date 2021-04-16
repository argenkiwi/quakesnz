package nz.co.codebros.quakesnz.map.model

import nz.co.codebros.quakesnz.core.data.Coordinates
import nz.co.vilemob.rxmodel.Reducer
import nz.co.vilemob.rxmodel.StateEventModel
import javax.inject.Inject
import javax.inject.Singleton

sealed class QuakeMapEvent {
    data class OnNewCoordinates(val coordinates: Coordinates?) : QuakeMapEvent()
}

data class QuakeMapState(val coordinates: Coordinates? = null)

object QuakeMapReducer : Reducer<QuakeMapState, QuakeMapEvent> {
    override fun apply(state: QuakeMapState, event: QuakeMapEvent): QuakeMapState {
        return when (event) {
            is QuakeMapEvent.OnNewCoordinates -> state.copy(coordinates = event.coordinates)
        }
    }
}

@Singleton
class QuakeMapModel @Inject constructor(

) : StateEventModel<QuakeMapState, QuakeMapEvent>(QuakeMapState(), QuakeMapReducer)
