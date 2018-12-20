package nz.co.codebros.quakesnz.detail.view

import androidx.lifecycle.Observer
import nz.co.codebros.quakesnz.detail.model.QuakeDetailState

class QuakeDetailStatePresenter(
        private val view: QuakeDetailView
) : Observer<QuakeDetailState> {
    override fun onChanged(t: QuakeDetailState?) {
        t?.apply {
            // TODO Show/hide progress bar.
            feature?.let { view.updateFeature(it) }
        }
    }
}
