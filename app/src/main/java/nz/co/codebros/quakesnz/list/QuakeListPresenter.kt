package nz.co.codebros.quakesnz.list

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.BasePresenter
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 9/07/15.
 */
class QuakeListPresenter(
        view: QuakeListView,
        private val featureRepository: FeatureRepository,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : BasePresenter<QuakeListView, Unit>(view) {

    override fun onViewCreated() {
        super.onViewCreated()
        addDisposable(featureRepository.subscribe(Consumer { view.selectFeature(it) }))
    }

    fun onFeatureSelected(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }
}