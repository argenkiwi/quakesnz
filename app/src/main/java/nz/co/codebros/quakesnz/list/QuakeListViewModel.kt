package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository
import nz.co.codebros.quakesnz.repository.FeatureRepository
import javax.inject.Inject

/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel(
        repository: FeatureCollectionRepository,
        featureRepository: FeatureRepository,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : ViewModel() {

    val state = MutableLiveData<QuakeListViewState>()
    private val disposables = CompositeDisposable()

    init {
        disposables.add(repository.observable.subscribe({
            state.value = QuakeListViewState(false, it.features)
        }))

        disposables.add(featureRepository.observable.subscribe({
            state.value = state.value?.copy(selectedFeature = it)
        }))
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun onRefresh() {
        state.value = QuakeListViewState(true)
        disposables.add(loadFeaturesInteractor.execute(Action {

        }, Consumer<Throwable> {
            state.value = QuakeListViewState(false, error = it)
        }))
    }

    fun onSelectFeature(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }

    class Factory @Inject constructor(
            private val repository: FeatureCollectionRepository,
            private val featureRepository: FeatureRepository,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = QuakeListViewModel(
                repository, featureRepository, interactor, selectFeatureInteractor
        ) as T
    }
}
