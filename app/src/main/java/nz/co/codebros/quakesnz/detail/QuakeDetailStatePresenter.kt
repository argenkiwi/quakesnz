package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.Observer

/**
 * Created by Leandro on 16/02/2018.
 */
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