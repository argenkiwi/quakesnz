package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 2/04/16.
 */
class GetFeaturesInteractorImpl(
        private val service: GeonetService,
        private val preferences: SharedPreferences
) : GetFeaturesInteractor {
    override fun execute(subscriber: SingleObserver<FeatureCollection>) {
        val mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"))
        service.getQuakes(mmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }
}
