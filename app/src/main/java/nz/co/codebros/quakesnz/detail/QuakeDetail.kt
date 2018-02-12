package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import ar.soflete.cycler.Reducer
import ar.soflete.cycler.StateEventModel
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

    class ViewModel(featureObservable: Observable<Result<Feature>>) : android.arch.lifecycle.ViewModel() {
        val model = StateEventModel<State, Result<Feature>>(State(), Companion)

        private val disposables = model.publish(featureObservable)

        override fun onCleared() {
            super.onCleared()
            disposables.dispose()
        }

        internal class Factory @Inject constructor(
                private val featureObservable: Observable<Result<Feature>>
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                    ViewModel(featureObservable) as T
        }

        companion object : Reducer<State, Result<Feature>> {
            override fun apply(state: State, event: Result<Feature>) = when (event) {
                is Result.Success -> state.copy(feature = event.value)
                is Result.Failure -> state.copy(throwable = event.throwable)
            }
        }
    }

    interface View {
        fun showDetails(feature: Feature)
        fun showLoadingError()
    }

    class Presenter(override val view: View) : nz.co.codebros.quakesnz.Presenter<View, State> {
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