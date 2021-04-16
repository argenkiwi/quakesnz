package nz.co.codebros.quakesnz.detail.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.runBlocking
import nz.co.codebros.quakesnz.core.usecase.LoadFeatureUseCase
import javax.inject.Inject

class QuakeDetailReactor @Inject constructor(
        private val loadFeatureUseCase: LoadFeatureUseCase,
) {

    fun react(event: QuakeDetailEvent): Single<QuakeDetailEvent>? = when (event) {
        is QuakeDetailEvent.LoadQuake -> with(event) {
            Single.fromCallable { runBlocking { loadFeatureUseCase.execute(publicId) } }
                    .map<QuakeDetailEvent> { QuakeDetailEvent.LoadQuakeComplete(it) }
                    .onErrorReturn { QuakeDetailEvent.LoadQuakeError(it) }
                    .observeOn(AndroidSchedulers.mainThread())
        }
        is QuakeDetailEvent.LoadQuakeComplete -> null
        is QuakeDetailEvent.LoadQuakeError -> null
    }
}
