package nz.co.codebros.quakesnz.detail.model

import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.ofType
import kotlinx.coroutines.runBlocking
import nz.co.codebros.quakesnz.core.usecase.LoadFeatureUseCase
import nz.co.codebros.quakesnz.scope.ActivityScope
import nz.co.codebros.quakesnz.util.BaseModel
import javax.inject.Inject

@ActivityScope
class QuakeDetailModel @Inject constructor(
        private val loadFeatureUseCase: LoadFeatureUseCase
) : BaseModel<QuakeDetailState, QuakeDetailEvent>(
        QuakeDetailState(false, null),
        QuakeDetailReducer
) {

    override fun subscribe(): Disposable = publish(eventObservable
            .ofType<QuakeDetailEvent.LoadQuake>()
            .map<QuakeDetailEvent> {
                runBlocking {
                    loadFeatureUseCase.execute(it.publicId)
                }.let { QuakeDetailEvent.LoadQuakeComplete(it) }
            }
            .onErrorReturn { QuakeDetailEvent.LoadQuakeError(it) })
}
