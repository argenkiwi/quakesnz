package nz.co.codebros.quakesnz.detail

import ar.soflete.cycler.Reducer
import nz.co.codebros.quakesnz.interactor.Result

/**
 * Created by Leandro on 16/02/2018.
 */
object QuakeDetailReducer : Reducer<QuakeDetailState, QuakeDetailEvent> {
    override fun apply(state: QuakeDetailState, event: QuakeDetailEvent): QuakeDetailState {
        return when (event) {
            is QuakeDetailEvent.LoadQuake -> state.copy(isLoading = true)
            is QuakeDetailEvent.LoadQuakeComplete -> state.copy(isLoading = false, feature = when (event.result) {
                is Result.Success -> event.result.value
                else -> state.feature
            })
            is QuakeDetailEvent.LoadQuakeError -> state.copy(isLoading = false)
        }
    }
}