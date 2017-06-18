package nz.co.codebros.quakesnz.interactor

import android.content.SharedPreferences
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository

/**
 * Created by leandro on 2/04/16.
 */
class LoadFeaturesInteractorImpl(
        private val preferences: SharedPreferences,
        private val repository: FeatureCollectionRepository
) : LoadFeaturesInteractor {
    override fun execute(observer: CompletableObserver) {
        val mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"))
        repository.load(mmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}
