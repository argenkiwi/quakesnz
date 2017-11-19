package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by Leandro on 27/10/2017.
 */

internal class QuakeDetailViewModel(
        featureObservable: Observable<Feature>,
        viewState: MutableLiveData<QuakeDetailViewState>
) : ViewModel() {
    val feature: LiveData<QuakeDetailViewState> = viewState

    private val disposable = featureObservable.subscribe({ viewState.value = Success(it) })

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    internal class Factory @Inject constructor(
            private val featureObservable: Observable<Feature>,
            private val viewState: MutableLiveData<QuakeDetailViewState>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
                QuakeDetailViewModel(featureObservable, viewState) as T
    }
}
