package nz.co.codebros.quakesnz.detail

import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.scope.ActivityScope
import nz.co.codebros.quakesnz.usecase.LoadFeatureUseCase
import nz.co.codebros.quakesnz.util.BaseModel
import javax.inject.Inject

/**
 * Created by Leandro on 23/02/2018.
 */
@ActivityScope
class QuakeDetailModel @Inject constructor(
        private val loadFeatureUseCase: LoadFeatureUseCase
) : BaseModel<QuakeDetailState, QuakeDetailEvent>(
        QuakeDetailState(false, null),
        QuakeDetailReducer
) {
    override fun subscribe() = publish(eventObservable
            .ofType<QuakeDetailEvent.LoadQuake>()
            .flatMap { loadFeatureUseCase.execute(it.publicId) }
            .map { QuakeDetailEvent.LoadQuakeComplete(it) as QuakeDetailEvent }
            .onErrorReturn { QuakeDetailEvent.LoadQuakeError(it) })
}
