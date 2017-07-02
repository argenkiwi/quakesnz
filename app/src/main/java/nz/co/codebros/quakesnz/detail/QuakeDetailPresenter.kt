package nz.co.codebros.quakesnz.detail

import android.os.Bundle
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.model.Feature
import nz.co.codebros.quakesnz.presenter.BasePresenter
import nz.co.codebros.quakesnz.repository.BundleRepository

/**
 * Created by leandro on 7/07/16.
 */
class QuakeDetailPresenter internal constructor(
        private val view: QuakeDetailView,
        private val repository: BundleRepository<Feature>,
        private val interactor: LoadFeatureInteractor
) : BasePresenter() {

    fun onRefresh(publicId: String) {
        addDisposable(interactor.execute(publicId, Action {
            // TODO Do something!
        }, Consumer<Throwable> { view.showLoadingError() })!!)
    }

    fun onShare(feature: Feature) {
        view.share(feature)
    }

    override fun onViewCreated() {
        addDisposable(repository.subscribe(Consumer { view.showDetails(it) }))
    }

    override fun onViewRestored(bundle: Bundle) {
        repository.publish(bundle)
        onViewCreated()
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        addDisposable(repository.subscribe(bundle))
    }

    override fun onDestroyView() {
        disposeAll()
    }
}
