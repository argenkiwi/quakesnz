package nz.co.codebros.quakesnz.interactor

import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.FeatureCollection

/**
 * Created by leandro on 10/06/17.
 */
interface LoadFeaturesInteractor {
    fun execute(): Observable<FeatureCollection>
}
