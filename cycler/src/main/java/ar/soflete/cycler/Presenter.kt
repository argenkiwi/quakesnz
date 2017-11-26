package ar.soflete.cycler

import android.arch.lifecycle.Observer

/**
 * Created by Leandro on 26/11/2017.
 */
abstract class Presenter<State, out View>(protected val view: View) : Observer<State>