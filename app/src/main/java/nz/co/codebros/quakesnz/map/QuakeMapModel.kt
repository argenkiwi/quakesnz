package nz.co.codebros.quakesnz.map

import ar.soflete.cycler.DisposableStateEventModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeMapModel @Inject constructor(
        featureObservable: Observable<Feature>
) : DisposableStateEventModel<QuakeMapState, QuakeMapEvent>(
        QuakeMapState(null), QuakeMapReducer
) {
    override val disposable: Disposable = publish(featureObservable
            .map { QuakeMapEvent.QuakeSelected(it) as QuakeMapEvent })
}