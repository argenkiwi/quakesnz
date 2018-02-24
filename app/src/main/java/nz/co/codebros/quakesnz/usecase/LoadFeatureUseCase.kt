package nz.co.codebros.quakesnz.usecase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by leandro on 18/06/17.
 */
class LoadFeatureUseCase @Inject constructor(
        private val service: GeonetService
) {
    fun execute(publicId: String): Observable<Result<Feature>> = service.getQuake(publicId)
            .map { (features) -> Result.Success(features.first()) as Result<Feature> }
            .onErrorReturn { Result.Failure(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}