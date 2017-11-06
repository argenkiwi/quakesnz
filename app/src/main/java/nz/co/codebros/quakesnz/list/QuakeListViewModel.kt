package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository

/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel(
        private val repository: FeatureCollectionRepository,
        private val loadFeaturesInteractor: LoadFeaturesInteractor
) : ViewModel() {

    val state: MutableLiveData<State> by lazy {
        val state = MutableLiveData<State>()
        disposables.add(repository.subscribe(Consumer {
            state.value = State(false, it.features)
        }))
        state
    }

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun onRefresh() {
        state.value = QuakeListViewModel.State(true)
        disposables.add(loadFeaturesInteractor.execute(Action {

        }, Consumer<Throwable> {
            state.value = QuakeListViewModel.State(false, error = it)
        }))
    }

    data class State(
            val isLoading: Boolean,
            val features: List<Feature>? = null,
            val error: Throwable? = null
    )

    internal class Factory(
            private val repository: FeatureCollectionRepository,
            private val interactor: LoadFeaturesInteractor
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
                QuakeListViewModel(repository, interactor) as T
    }
}
