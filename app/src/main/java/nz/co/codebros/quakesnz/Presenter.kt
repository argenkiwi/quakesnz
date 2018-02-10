package nz.co.codebros.quakesnz

import android.arch.lifecycle.Observer

/**
 * Created by Leandro on 26/11/2017.
 */
interface Presenter<out View, State> : Observer<State> {
    val view: View
    override fun onChanged(state: State?)
}