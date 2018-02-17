package nz.co.codebros.quakesnz.detail

import ar.soflete.cycler.DisposableStateEventModel
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.usecase.LoadFeatureUseCase
import nz.co.codebros.quakesnz.scope.ActivityScope
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
@ActivityScope
class QuakeDetailModel @Inject constructor(
        loadFeatureUseCase: LoadFeatureUseCase
) : DisposableStateEventModel<QuakeDetailState, QuakeDetailEvent>(
        QuakeDetailState(false, null), QuakeDetailReducer
) {
    override val disposable: Disposable = publish(eventObservable
            .ofType<QuakeDetailEvent.LoadQuake>()
            .flatMap { loadFeatureUseCase.execute(it.publicId) }
            .map { QuakeDetailEvent.LoadQuakeComplete(it) as QuakeDetailEvent }
            .onErrorReturn { QuakeDetailEvent.LoadQuakeError(it) })
}