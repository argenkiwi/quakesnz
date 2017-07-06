package nz.co.codebros.quakesnz.detail

import android.os.Bundle
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.presenter.BasePresenter
import nz.co.codebros.quakesnz.repository.BundleRepository

/**
 * Created by leandro on 7/07/16.
 */
class QuakeDetailPresenter(
        view: QuakeDetailView,
        private val repository: BundleRepository<Feature>,
        private val interactor: LoadFeatureInteractor
) : BasePresenter<QuakeDetailView, QuakeDetailProps>(view) {

    override fun onInit(props: QuakeDetailProps?) {
        super.onInit(props)
        props?.let {
            addDisposable(interactor.execute(it.publicId, Action {
                // TODO Show progress indicator?
            }, Consumer<Throwable> {
                view.showLoadingError()
            }))
        }
    }

    override fun onViewCreated() {
        addDisposable(repository.subscribe(Consumer { view.showDetails(it) }))
    }

    override fun onRestoreState(bundle: Bundle) {
        repository.publish(bundle)
    }

    override fun onSaveState(bundle: Bundle) {
        addDisposable(repository.subscribe(bundle))
    }

    fun onShare(feature: Feature) {
        view.share(feature)
    }
}
