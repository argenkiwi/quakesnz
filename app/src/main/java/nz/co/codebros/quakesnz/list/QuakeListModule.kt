package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
object QuakeListModule {

    @JvmStatic
    @Provides
    internal fun selectFeatureInteractor(
            featureSubject: Subject<Feature>
    ): SelectFeatureInteractor = SelectFeatureInteractorImpl(featureSubject)

    @JvmStatic
    @Provides
    internal fun viewModel(
            fragment: QuakeListFragment,
            factory: QuakeListViewModel.Factory
    ) = ViewModelProviders.of(fragment, factory).get(QuakeListViewModel::class.java)
}
