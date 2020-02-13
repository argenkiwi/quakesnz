package nz.co.codebros.quakesnz.detail.view

import androidx.lifecycle.Observer
import nz.co.codebros.quakesnz.core.usecase.Result
import nz.co.codebros.quakesnz.detail.model.QuakeDetailEvent

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
