package nz.co.codebros.quakesnz.map

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 16/02/2018.
 */
sealed class QuakeMapEvent {
    data class QuakeSelected(val feature: Feature) : QuakeMapEvent()
}