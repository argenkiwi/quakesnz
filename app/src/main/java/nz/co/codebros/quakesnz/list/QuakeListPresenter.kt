package nz.co.codebros.quakesnz.list

import android.os.Bundle
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.core.model.FeatureCollection
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.presenter.BasePresenter
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 9/07/15.
 */
class QuakeListPresenter (
        view: QuakeListView,
        private val featureCollectionRepository: FeatureCollectionRepository,
        private val featureRepository: FeatureRepository,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : BasePresenter<QuakeListView, Unit>(view) {

    private val featureCollectionConsumer = Consumer<FeatureCollection> { (features) ->
        view.listQuakes(features)
    }

    override fun onInit(props: Unit?) {
        super.onInit(props)
        view.showProgress()
        addDisposable(loadFeaturesInteractor.execute(Action {
            view.hideProgress()
        }, Consumer<Throwable> {
            view.hideProgress()
            view.showError()
        }))
    }

    override fun onViewCreated() {
        super.onViewCreated()
        addDisposable(featureCollectionRepository.subscribe(featureCollectionConsumer))
        addDisposable(featureRepository.subscribe(Consumer { view.selectFeature(it) }))
    }

    override fun onRestoreState(bundle: Bundle) {
        super.onRestoreState(bundle)
        featureCollectionRepository.publish(bundle)
    }

    override fun onSaveState(bundle: Bundle) {
        super.onSaveState(bundle)
        addDisposable(featureCollectionRepository.subscribe(bundle))
    }

    fun onFeatureSelected(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }
}