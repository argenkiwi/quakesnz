package ar.soflete.cycler

import io.reactivex.functions.BiFunction

/**
 * Created by Leandro on 30/01/2018.
 */
interface Reducer<S, E> : BiFunction<S, E, S> {
    override fun apply(state: S, event: E): S
}