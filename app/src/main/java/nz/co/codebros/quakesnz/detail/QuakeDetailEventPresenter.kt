package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.Observer
import nz.co.codebros.quakesnz.core.usecase.Result

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeDetailEventPresenter(
        private val view: QuakeDetailView
) : Observer<QuakeDetailEvent> {
    override fun onChanged(t: QuakeDetailEvent?) {
        when (t) {
            is QuakeDetailEvent.LoadQuakeError -> view.showLoadingError()
            is QuakeDetailEvent.LoadQuakeComplete -> when (t.result) {
                is Result.Failure -> view.showLoadingError()
            }
        }
    }
}