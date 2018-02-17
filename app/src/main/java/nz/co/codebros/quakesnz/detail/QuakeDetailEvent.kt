package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.Result

/**
 * Created by Leandro on 16/02/2018.
 */
sealed class QuakeDetailEvent {
    data class LoadQuake(val publicId: String) : QuakeDetailEvent()
    data class LoadQuakeComplete(val result: Result<Feature>) : QuakeDetailEvent()
    data class LoadQuakeError(val error: Throwable) : QuakeDetailEvent()
}