package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.Result

/**
 * Created by Leandro on 27/11/2017.
 */
object QuakeDetailReducer {
    fun reduce(state: QuakeDetail.State, event: Result<Feature>) = when (event) {
        is Result.Success -> state.copy(feature = event.value)
        is Result.Failure -> state.copy(throwable = event.throwable)
    }
}