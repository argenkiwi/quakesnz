package nz.co.codebros.quakesnz.interactor

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 18/06/17.
 */

class LoadFeatureInteractorImpl(
        private val service: GeonetService,
        private val repository: FeatureRepository
) : LoadFeatureInteractor {
    override fun execute(
            publicId: String,
            onComplete: Action,
            onError: Consumer<Throwable>
    ): Disposable = service.getQuake(publicId)
            .map { (features) -> features[0] }
            .doOnSuccess { repository.observer.onNext(it) }
            .toCompletable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onComplete, onError)
}
