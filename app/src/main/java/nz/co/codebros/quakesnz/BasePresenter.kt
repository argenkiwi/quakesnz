package nz.co.codebros.quakesnz

import android.arch.lifecycle.Observer

/**
 * Created by Leandro on 26/11/2017.
 */
abstract class BasePresenter<State, out View>(protected val view: View) : Observer<State>