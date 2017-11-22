package nz.co.codebros.quakesnz.interactor

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import javax.inject.Inject

/**
 * Created by leandro on 2/04/16.
 */
class LoadFeaturesInteractorImpl @Inject constructor(
        private val service: GeonetService
) : LoadFeaturesInteractor {
    override fun execute(mmi: Int) = service
            .getQuakes(mmi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
