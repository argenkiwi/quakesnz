package nz.co.codebros.quakesnz.core.usecase

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.core.service.GeonetService
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

class LoadFeatureUseCase @Inject constructor(
        private val service: GeonetService
) {
    fun execute(publicId: String): Single<Result<Feature>> = service.getQuake(publicId)
            .map<Result<Feature>> { (features) -> Result.Success(features.first()) }
            .onErrorReturn { Result.Failure(it) }
            .subscribeOn(Schedulers.io())
}
