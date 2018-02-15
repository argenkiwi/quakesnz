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
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.detail.QuakeDetailEvent
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment
import nz.co.codebros.quakesnz.detail.QuakeDetailModel
import nz.co.codebros.quakesnz.detail.QuakeDetailModule
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl
import nz.co.codebros.quakesnz.interactor.Result
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.map.QuakeMapModule
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
        private val EXTRA_FEATURE = "extra_feature"
        fun newIntent(
                context: Context,
                feature: Feature
        ): Intent = Intent(context, FeatureDetailActivity::class.java)
                .putExtra(EXTRA_FEATURE, feature)
    }

    internal class ViewModel(
            val fragmentInjector: DispatchingAndroidInjector<Fragment>,
            private val quakeDetailModel: QuakeDetailModel
    ) : android.arch.lifecycle.ViewModel() {

        class Factory @Inject constructor(
                private val fragmentInjector: DispatchingAndroidInjector<Fragment>,
                private val quakeDetailModel: QuakeDetailModel
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                    ViewModel(fragmentInjector, quakeDetailModel) as T
        }

        fun loadFeature(feature: Feature) {
            quakeDetailModel.publish(QuakeDetailEvent.LoadQuakeComplete(Result.Success(feature)))
        }

        fun loadFeature(publicId: String) {
            quakeDetailModel.publish(QuakeDetailEvent.LoadQuake(publicId))
        }
    }

    @dagger.Module
    internal abstract class Module {
        @ContributesAndroidInjector(modules = [QuakeDetailModule::class])
        internal abstract fun quakeDetailFragment(): QuakeDetailFragment

        @ContributesAndroidInjector(modules = [QuakeMapModule::class])
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

            @JvmStatic
            @Provides
            fun viewModel(activity: FeatureDetailActivity, factory: ViewModel.Factory) =
                    ViewModelProviders.of(activity, factory).get(ViewModel::class.java)
        }
    }
}
