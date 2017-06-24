package nz.co.codebros.quakesnz.interactor

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
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
    ): Disposable? {
        return service.getQuake(publicId)
                .map { featureCollection -> featureCollection.features }
                .map { features -> features[0] }
                .doAfterSuccess { repository.publish(it) }
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onComplete, onError)
    }
}
