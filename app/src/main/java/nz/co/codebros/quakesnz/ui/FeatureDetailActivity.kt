package nz.co.codebros.quakesnz.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.view.MenuItem
import ar.soflete.daggerlifecycle.DaggerViewModel
import ar.soflete.daggerlifecycle.appcompat.DaggerViewModelActivity
import dagger.Binds
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.detail.QuakeDetailEvent
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment
import nz.co.codebros.quakesnz.detail.QuakeDetailModel
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl
import nz.co.codebros.quakesnz.interactor.Result
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import javax.inject.Inject

class FeatureDetailActivity : DaggerViewModelActivity<FeatureDetailActivity.ViewModel>() {

    override val viewModelClass: Class<ViewModel>
        get() = ViewModel::class.java

    private lateinit var viewModel: FeatureDetailActivity.ViewModel

    override fun onBindViewModel(viewModel: ViewModel) {
        super.onBindViewModel(viewModel)
        this.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        when (savedInstanceState) {
            null -> {
                supportFragmentManager.beginTransaction()
                        .add(android.R.id.content, QuakeDetailFragment())
                        .commit()

                intent.let {
                    when {
                        it.data != null -> viewModel.loadFeature(it.data.lastPathSegment)
                        else -> viewModel.loadFeature(it.getParcelableExtra<Feature>(EXTRA_FEATURE))
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            NavUtils.getParentActivityIntent(this)?.let {
                when {
                    NavUtils.shouldUpRecreateTask(this, it) ->
                        TaskStackBuilder.create(this)
                                .addNextIntentWithParentStack(it)
                                .startActivities()
                    else -> NavUtils.navigateUpTo(this, it)
                }
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    companion object {
        private const val EXTRA_FEATURE = "extra_feature"
        fun newIntent(
                context: Context,
                feature: Feature
        ): Intent = Intent(context, FeatureDetailActivity::class.java)
                .putExtra(EXTRA_FEATURE, feature)
    }

    class ViewModel @Inject constructor(
            private val quakeDetailModel: QuakeDetailModel
    ) : DaggerViewModel() {

        fun loadFeature(feature: Feature) {
            quakeDetailModel.publish(QuakeDetailEvent.LoadQuakeComplete(Result.Success(feature)))
        }

        fun loadFeature(publicId: String) {
            quakeDetailModel.publish(QuakeDetailEvent.LoadQuake(publicId))
        }
    }

    @dagger.Module
    internal abstract class Module {
        @ContributesAndroidInjector
        internal abstract fun quakeDetailFragment(): QuakeDetailFragment

        @ContributesAndroidInjector
        internal abstract fun quakeMapFragment(): QuakeMapFragment

        @Binds
        internal abstract fun loadFeatureInteractor(
                loadFeatureInteractorImpl: LoadFeatureInteractorImpl
        ): LoadFeatureInteractor

        @dagger.Module
        internal companion object {

            @JvmStatic
            @Provides
            fun featureObservable(
                    quakeDetailModel: QuakeDetailModel
            ): Observable<Feature> = quakeDetailModel.stateObservable.map { it.feature }
        }
    }
}
