package nz.co.codebros.quakesnz.core.usecase

import android.content.SharedPreferences
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import nz.co.codebros.quakesnz.core.service.GeonetService
import javax.inject.Inject

class LoadFeaturesUseCase @Inject constructor(
        private val service: GeonetService,
        private val sharedPreferences: SharedPreferences
) {
    fun execute(): Single<FeatureCollection> {
        val mmi = sharedPreferences.getInt("pref_intensity", 3)
        return service.getQuakes(mmi).subscribeOn(Schedulers.io())
    }
}
