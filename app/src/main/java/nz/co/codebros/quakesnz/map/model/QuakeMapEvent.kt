package nz.co.codebros.quakesnz.map.model

import nz.co.codebros.quakesnz.core.data.Coordinates

sealed class QuakeMapEvent {
    data class OnNewCoordinates(val coordinates: Coordinates?) : QuakeMapEvent()
}
