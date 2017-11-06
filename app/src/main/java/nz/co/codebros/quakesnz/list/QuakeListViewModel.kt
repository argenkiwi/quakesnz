package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel(
        private val repository: FeatureCollectionRepository,
        private val featureRepository: FeatureRepository,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : ViewModel() {

    val state: MutableLiveData<State> by lazy {
        val state = MutableLiveData<State>()

        disposables.add(repository.subscribe(Consumer {
            state.value = State(false, it.features)
        }))

        disposables.add(featureRepository.subscribe(Consumer {
            state.value = state.value?.copy(selectedFeature = it)
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

    fun onSelectFeature(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }

    data class State(
            val isLoading: Boolean,
            val features: List<Feature>? = null,
            val selectedFeature: Feature? = null,
            val error: Throwable? = null
    )

    internal class Factory(
            private val repository: FeatureCollectionRepository,
            private val featureRepository: FeatureRepository,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = QuakeListViewModel(
                repository, featureRepository, interactor, selectFeatureInteractor
        ) as T
    }
}
