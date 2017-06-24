package nz.co.codebros.quakesnz.list

import android.os.Bundle

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.model.Feature
import nz.co.codebros.quakesnz.model.FeatureCollection
import nz.co.codebros.quakesnz.presenter.BasePresenter
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository

/**
 * Created by leandro on 9/07/15.
 */
class QuakeListPresenter internal constructor(
        private val view: QuakeListView,
        private val featureCollectionRepository: FeatureCollectionRepository,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : BasePresenter() {
    private val featureCollectionConsumer = Consumer<FeatureCollection> { featureCollection ->
        view.listQuakes(featureCollection.features)
    }

    fun onRefresh() {
        view.showProgress()
        addDisposable(loadFeaturesInteractor.execute(Action {
            view.hideProgress()
        }, Consumer<Throwable> {
            view.hideProgress()
            view.showError()
        })!!)
    }

    fun onFeatureSelected(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }

    override fun onViewCreated() {
        addDisposable(featureCollectionRepository.subscribe(featureCollectionConsumer))
    }

    override fun onViewRestored(bundle: Bundle) {
        featureCollectionRepository.publish(bundle)
        onViewCreated()
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        addDisposable(featureCollectionRepository.subscribe(bundle))
    }

    override fun onDestroyView() {
        disposeAll()
    }
}