package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.model.FeatureCollection
import nz.co.codebros.quakesnz.repository.Repository

/**
 * Created by leandro on 2/04/16.
 */
class LoadFeaturesInteractorImpl(
        private val preferences: SharedPreferences,
        private val service: GeonetService,
        private val repository: Repository<FeatureCollection>
) : LoadFeaturesInteractor {
    override fun execute(onSuccess: Action, onError: Consumer<Throwable>): Disposable? {
        return completable()?.subscribe(onSuccess, onError)
    }

    override fun execute() {
        completable()?.subscribe()
    }

    private fun completable(): Completable? {
        val mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"))
        return service.getQuakes(mmi)
                .doAfterSuccess { repository.publish(it) }
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
