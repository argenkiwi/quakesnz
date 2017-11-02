package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.core.model.FeatureCollection
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository

/**
 * Created by Leandro on 28/10/2017.
 */

internal class QuakeListViewModel(private val repository: FeatureCollectionRepository) : ViewModel() {
    val features: MutableLiveData<List<Feature>> by lazy {
        val features = MutableLiveData<List<Feature>>()
        disposable = repository.subscribe(Consumer { features.value = it.features })
        features
    }

    private var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    internal class Factory(
            private val repository: FeatureCollectionRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
                QuakeListViewModel(repository) as T
    }
}
