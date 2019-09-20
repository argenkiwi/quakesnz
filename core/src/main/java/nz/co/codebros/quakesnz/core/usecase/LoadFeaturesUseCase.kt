package nz.co.codebros.quakesnz.core.usecase

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import javax.inject.Inject

class LoadFeaturesUseCase @Inject constructor(
        private val service: GeonetService,
        private val sharedPreferences: SharedPreferences
) {
    fun execute(): Observable<FeatureCollection> = service
            .getQuakes((sharedPreferences.getString("pref_intensity", null) ?: "3").toInt())
            .subscribeOn(Schedulers.io())
}