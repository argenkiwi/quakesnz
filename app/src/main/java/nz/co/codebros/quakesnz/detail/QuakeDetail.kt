package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import ar.soflete.cycler.StateEventModel
import dagger.Provides
import io.reactivex.Observable
import nz.co.codebros.quakesnz.BasePresenter
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

    class ViewModel(val model: Model) : android.arch.lifecycle.ViewModel() {

        override fun onCleared() {
            super.onCleared()
            model.dispose()
        }

        internal class Factory @Inject constructor(
                private val model: Model
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                    ViewModel(model) as T
        }
    }

    interface View {
        fun showDetails(feature: Feature)
        fun showLoadingError()
    }

    object Reducer : ar.soflete.cycler.Reducer<State, Result<Feature>> {
        override fun apply(state: State, event: Result<Feature>) = when (event) {
            is Result.Success -> state.copy(feature = event.value)
            is Result.Failure -> state.copy(throwable = event.throwable)
        }
    }

    class Model @Inject constructor(
            featureObservable: Observable<Result<Feature>>
    ) : StateEventModel<State, Result<Feature>>(State(), Reducer) {
        init {
            publish(featureObservable)
        }
    }

    class Presenter(view: View) : BasePresenter<State, View>(view) {
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