package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import javax.inject.Inject

/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel(
        featuresObservable: Observable<List<Feature>>,
        selectedFeatureObservable: Observable<Feature>,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : ViewModel() {

    val state = MutableLiveData<QuakeListViewState>()
    private val disposables = CompositeDisposable()

    init {
        disposables.add(featuresObservable.subscribe({
            state.value = QuakeListViewState(false, it)
        }))

        disposables.add(selectedFeatureObservable.subscribe({
            state.value = state.value?.copy(selectedFeature = it)
        }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun onRefresh() {
        disposables.add(loadFeaturesInteractor.execute()
                .toObservable<QuakeListViewState>()
                .startWith(QuakeListViewState(true))
                .onErrorReturn { QuakeListViewState(false, error = it) }
                .subscribe({ state.value = it }))
    }

    fun onSelectFeature(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }

    class Factory @Inject constructor(
            private val featuresObservable: Observable<List<Feature>>,
            private val selectedFeatureObservable: Observable<Feature>,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = QuakeListViewModel(
                featuresObservable, selectedFeatureObservable, interactor, selectFeatureInteractor
        ) as T
    }
}
