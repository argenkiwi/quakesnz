package ar.soflete.cycler

/**
 * Created by Leandro on 26/11/2017.
 */
interface Reducer<State, in Event> {
    fun reduce(state: State, event: Event): State
}