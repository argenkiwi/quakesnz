package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 2/04/16.
 */
class GetFeaturesInteractorImpl(
        private val service: GeonetService,
        private val preferences: SharedPreferences,
        private val featureCollectionObserver: Observer<FeatureCollection>
) : GetFeaturesInteractor {
    override fun execute(subscriber: CompletableObserver) {
        val mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"))
        service.getQuakes(mmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterSuccess { featureCollectionObserver.onNext(it) }
                .toCompletable()
                .subscribe(subscriber)
    }
}
