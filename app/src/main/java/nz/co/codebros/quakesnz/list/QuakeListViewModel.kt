package nz.co.codebros.quakesnz.list

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.extension.reactIn
import nz.co.codebros.quakesnz.core.extension.reduceIn
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListReactor
import nz.co.codebros.quakesnz.list.model.QuakeListState
import nz.co.codebros.quakesnz.list.model.reduce
import nz.co.codebros.quakesnz.map.model.QuakeMapEvent
import nz.co.codebros.quakesnz.map.model.QuakeMapModel
import nz.co.codebros.quakesnz.util.changesFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class QuakeListViewModel @Inject constructor(
    private val events: MutableSharedFlow<QuakeListEvent>,
    state: MutableStateFlow<QuakeListState>,
    reactor: QuakeListReactor,
    sharedPreferences: SharedPreferences,
    quakeMapModel: QuakeMapModel
) : ViewModel() {

    val liveEvents
        get() = events.asLiveData()

    val liveState = state.asLiveData()

    private val disposables = CompositeDisposable()

    init {

        events.reactIn(viewModelScope, reactor::react)
        state.reduceIn(viewModelScope, events, ::reduce)

        viewModelScope.launch { events.emit(QuakeListEvent.RefreshQuakes) }

        viewModelScope.launch {
            sharedPreferences.changesFlow()
                .filter { it == "pref_intensity" }
                .collect { events.emit(QuakeListEvent.RefreshQuakes) }
        }

        viewModelScope.launch {
            state.map { it.selectedFeature?.geometry?.coordinates }.collect {
                it?.let {
                    quakeMapModel.publish(QuakeMapEvent.OnNewCoordinates(it))
                } ?: Log.w(QuakeListViewModel::class.simpleName, "Coordinates are null.")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun refreshQuakes() {
        viewModelScope.launch { events.emit(QuakeListEvent.RefreshQuakes) }
    }

    fun selectQuake(feature: Feature) {
        viewModelScope.launch { events.emit(QuakeListEvent.SelectQuake(feature)) }
    }
}
