package nz.co.codebros.quakesnz.core.usecase

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

class LoadFeatureUseCase @Inject constructor(
        private val service: GeonetService
) {
    fun execute(publicId: String): Observable<Result<Feature>> = service.getQuake(publicId)
            .map { (features) -> Result.Success(features.first()) as Result<Feature> }
            .onErrorReturn { Result.Failure(it) }
            .subscribeOn(Schedulers.io())
}
