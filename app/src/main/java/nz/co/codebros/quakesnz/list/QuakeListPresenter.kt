package nz.co.codebros.quakesnz.list

import android.os.Bundle
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.core.model.FeatureCollection
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.core.BasePresenter
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository
import nz.co.codebros.quakesnz.repository.FeatureRepository
import javax.inject.Inject

/**
 * Created by leandro on 9/07/15.
 */
class QuakeListPresenter @Inject constructor(
        view: QuakeListView,
        private val featureRepository: FeatureRepository,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : BasePresenter<QuakeListView, Unit>(view) {

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
        addDisposable(featureRepository.subscribe(Consumer { view.selectFeature(it) }))
    }

    fun onFeatureSelected(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }
}