package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import javax.inject.Inject

/**
 * Created by leandro on 2/04/16.
 */
class LoadFeaturesInteractorImpl @Inject constructor(
        private val service: GeonetService,
        private val sharedPreferences: SharedPreferences
) : LoadFeaturesInteractor {
    override fun execute(): Observable<FeatureCollection> {
        val mmi = sharedPreferences.getString("pref_intensity", "3").toInt()
        return service
                .getQuakes(mmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
