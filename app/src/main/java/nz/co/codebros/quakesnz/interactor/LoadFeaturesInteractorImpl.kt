package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.Repository
import nz.co.codebros.quakesnz.core.data.FeatureCollection

/**
 * Created by leandro on 2/04/16.
 */
class LoadFeaturesInteractorImpl(
        private val preferences: SharedPreferences,
        private val service: GeonetService,
        private val repository: Repository<FeatureCollection>
) : LoadFeaturesInteractor {
    override fun execute() = service
            .getQuakes(Integer.parseInt(preferences.getString("pref_intensity", "3")))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { repository.observer.onNext(it) }
            .toCompletable()
}
