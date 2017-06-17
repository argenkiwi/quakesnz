package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection
import nz.co.codebros.quakesnz.publisher.Publisher

/**
 * Created by leandro on 2/04/16.
 */
class GetFeaturesInteractorImpl(
        private val service: GeonetService,
        private val preferences: SharedPreferences,
        private val publisher: Publisher<FeatureCollection>
) : GetFeaturesInteractor {
    override fun execute(observer: CompletableObserver) {
        val mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"))
        service.getQuakes(mmi)
                .doAfterSuccess { publisher.publish(it) }
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}
