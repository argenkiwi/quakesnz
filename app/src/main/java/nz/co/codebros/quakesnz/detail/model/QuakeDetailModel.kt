package nz.co.codebros.quakesnz.detail.model

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.core.usecase.LoadFeatureUseCase
import nz.co.codebros.quakesnz.map.model.QuakeMapEvent
import nz.co.codebros.quakesnz.map.model.QuakeMapModel
import nz.co.codebros.quakesnz.util.BaseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuakeDetailModel @Inject constructor(
        private val loadFeatureUseCase: LoadFeatureUseCase,
        private val quakeMapModel: QuakeMapModel,
) : BaseModel<QuakeDetailState, QuakeDetailEvent>(
        QuakeDetailState(false, null), QuakeDetailReducer
) {

    override fun subscribe(): Disposable = CompositeDisposable(
            publish(
                    eventObservable.ofType<QuakeDetailEvent.LoadQuake>()
                            .flatMap {
                                loadFeatureUseCase.execute(it.publicId).observeOn(AndroidSchedulers.mainThread())
                            }
                            .map { QuakeDetailEvent.LoadQuakeComplete(it) as QuakeDetailEvent }
                            .onErrorReturn { QuakeDetailEvent.LoadQuakeError(it) }

            ),
            stateObservable
                    .map { it.feature?.geometry?.coordinates }
                    .distinct()
                    .subscribe({
                        quakeMapModel.publish(QuakeMapEvent.OnNewCoordinates(it))
                    }, {
                        Log.e(QuakeDetailModel::class.simpleName, "Coordinates are null.", it)
                    })
    )
}
