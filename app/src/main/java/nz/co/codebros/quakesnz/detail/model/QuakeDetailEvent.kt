package nz.co.codebros.quakesnz.detail.model

import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.usecase.Result

sealed class QuakeDetailEvent {
    data class LoadQuake(val publicId: String) : QuakeDetailEvent()
    data class LoadQuakeComplete(val result: Result<Feature>) : QuakeDetailEvent()
    data class LoadQuakeError(val error: Throwable) : QuakeDetailEvent()
}
