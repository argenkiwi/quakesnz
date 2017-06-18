package nz.co.codebros.quakesnz.interactor

import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 18/06/17.
 */

class LoadFeatureInteractorImpl(private val repository: FeatureRepository) : LoadFeatureInteractor {
    override fun execute(publicID: String, completableObserver: CompletableObserver) {
        repository.load(publicID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver)
    }
}
