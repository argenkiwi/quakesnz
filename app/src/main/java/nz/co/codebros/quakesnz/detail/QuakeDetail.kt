package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import ar.soflete.cycler.ReactiveViewModel
import dagger.Provides
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.Result
import javax.inject.Inject

/**
 * Created by Leandro on 25/11/2017.
 */
interface QuakeDetail {
    data class State(
            val feature: Feature? = null,
            val throwable: Throwable? = null
    )

    class ViewModel(
            featureObservable: Observable<Result<Feature>>
    ) : ReactiveViewModel<State, Result<Feature>>(State(), QuakeDetailReducer) {

        init {
            subscribe(featureObservable)
        }

        internal class Factory @Inject constructor(
                private val featureObservable: Observable<Result<Feature>>
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                    ViewModel(featureObservable) as T
        }
    }

    interface View {
        fun showDetails(feature: Feature)
        fun showLoadingError()
    }

    class Presenter(view: View) : ar.soflete.cycler.Presenter<State, View>(view) {
        override fun onChanged(state: State?) {
            state?.apply {
                feature?.let { view.showDetails(it) }
                throwable?.let { view.showLoadingError() }
            }
        }
    }

    @dagger.Module
    object Module {
        @JvmStatic
        @Provides
        internal fun viewModel(
                fragment: QuakeDetailFragment,
                factory: ViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(ViewModel::class.java)
    }
}