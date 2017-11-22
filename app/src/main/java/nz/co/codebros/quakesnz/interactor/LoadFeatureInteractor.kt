package nz.co.codebros.quakesnz.interactor

import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by leandro on 10/06/17.
 */
interface LoadFeatureInteractor {
    fun execute(publicId: String): Observable<Feature>
}
