package nz.co.codebros.quakesnz.detail

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.BasePresenter
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import javax.inject.Inject

/**
 * Created by leandro on 7/07/16.
 */
class QuakeDetailPresenter @Inject constructor(
        view: QuakeDetailView,
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
}
