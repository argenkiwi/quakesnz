package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import dagger.Binds
import dagger.Provides
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.detail.QuakeDetail
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl
import nz.co.codebros.quakesnz.interactor.Result
import nz.co.codebros.quakesnz.map.QuakeMap
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import javax.inject.Inject

class FeatureDetailActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var viewModel: ViewModel

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = viewModel.fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        try {
            viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        } catch (e: Throwable) {
            AndroidInjection.inject(this)
        }

        if (savedInstanceState == null) supportFragmentManager.beginTransaction()
                .add(android.R.id.content, QuakeDetailFragment())
                .commit()
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
        private val EXTRA_FEATURE = "extra_feature"
        fun newIntent(
                context: Context,
                feature: Feature
        ): Intent = Intent(context, FeatureDetailActivity::class.java)
                .putExtra(EXTRA_FEATURE, feature)
    }

    internal class ViewModel(
            val fragmentInjector: DispatchingAndroidInjector<Fragment>
    ) : android.arch.lifecycle.ViewModel() {

        class Factory @Inject constructor(
                private val fragmentInjector: DispatchingAndroidInjector<Fragment>
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                    ViewModel(fragmentInjector) as T
        }
    }

    @dagger.Module
    internal abstract class Module {
        @ContributesAndroidInjector(modules = arrayOf(
                QuakeDetail.Module::class
        ))
        internal abstract fun quakeDetailFragment(): QuakeDetailFragment

        @ContributesAndroidInjector(modules = arrayOf(
                QuakeMap.Module::class
        ))
        internal abstract fun quakeMapFragment(): QuakeMapFragment


        @Binds
        abstract fun loadFeatureInteractor(
                loadFeatureInteractorImpl: LoadFeatureInteractorImpl
        ): LoadFeatureInteractor

        @dagger.Module
        internal companion object {
            @JvmStatic
            @Provides
            fun featureResultObservable(
                    activity: FeatureDetailActivity,
                    loadFeatureInteractor: LoadFeatureInteractor
            ): Observable<Result<Feature>> = activity.intent.let {
                when {
                    it.data != null -> loadFeatureInteractor.execute(it.data.lastPathSegment)
                    else -> Observable.just(it.getParcelableExtra<Feature>(EXTRA_FEATURE))
                            .map { Result.Success(it) }
                }
            }

            @JvmStatic
            @Provides
            fun featureObservable(
                    featureResultObservable: Observable<Result<Feature>>
            ): Observable<Feature> = featureResultObservable
                    .filter({ it is Result.Success })
                    .map { it as Result.Success }
                    .map { it.value }

            @JvmStatic
            @Provides
            fun viewModel(activity: FeatureDetailActivity, factory: ViewModel.Factory) =
                    ViewModelProviders.of(activity, factory).get(ViewModel::class.java)
        }
    }
}
