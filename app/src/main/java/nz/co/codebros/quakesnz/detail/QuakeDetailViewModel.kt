package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.repository.FeatureRepository
import javax.inject.Inject

/**
 * Created by Leandro on 27/10/2017.
 */

internal class QuakeDetailViewModel(
        val feature: MutableLiveData<Feature>,
        repository: FeatureRepository
) : ViewModel() {

    private val disposable = repository.subscribe(Consumer { feature.value = it })

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    internal class Factory @Inject constructor(
            private val featureLiveData: MutableLiveData<Feature>,
            private val repository: FeatureRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
                QuakeDetailViewModel(featureLiveData, repository) as T
    }
}
