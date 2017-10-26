package nz.co.codebros.quakesnz.detail

import android.os.Bundle
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.BasePresenter
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.repository.FeatureRepository
import javax.inject.Inject

/**
 * Created by leandro on 7/07/16.
 */
class QuakeDetailPresenter @Inject constructor(
        view: QuakeDetailView,
        private val repository: FeatureRepository,
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
