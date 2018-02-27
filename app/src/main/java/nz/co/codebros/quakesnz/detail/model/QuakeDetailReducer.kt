package nz.co.codebros.quakesnz.detail.model

import nz.co.codebros.quakesnz.core.usecase.Result
import nz.co.vilemob.rxmodel.Reducer

/**
 * Created by Leandro on 16/02/2018.
 */
object QuakeDetailReducer : Reducer<QuakeDetailState, QuakeDetailEvent> {
    override fun apply(state: QuakeDetailState, event: QuakeDetailEvent) = when (event) {
        is QuakeDetailEvent.LoadQuake -> state.copy(isLoading = true)
        is QuakeDetailEvent.LoadQuakeComplete -> state.run {
            copy(isLoading = false, feature = when (event.result) {
                is Result.Success -> event.result.value
                else -> feature
            })
        }
        is QuakeDetailEvent.LoadQuakeError -> state.copy(isLoading = false)
    }
}
