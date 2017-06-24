package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.GeonetService
import nz.co.codebros.quakesnz.model.FeatureCollection

/**
 * Created by leandro on 2/04/16.
 */
class LoadFeaturesInteractorImpl(
        private val preferences: SharedPreferences,
        private val service: GeonetService,
        private val observer: Observer<FeatureCollection>
) : LoadFeaturesInteractor {
    override fun execute(onSuccess: Action, onError: Consumer<Throwable>): Disposable? {
        val mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"))
        return service.getQuakes(mmi)
                .doAfterSuccess { observer.onNext(it) }
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}
