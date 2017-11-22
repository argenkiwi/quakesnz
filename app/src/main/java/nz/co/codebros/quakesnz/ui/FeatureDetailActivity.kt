package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.Provides
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment
import nz.co.codebros.quakesnz.detail.QuakeDetailModule
import nz.co.codebros.quakesnz.map.QuakeMap
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import javax.inject.Inject

class FeatureDetailActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var viewModel: ViewModel

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = viewModel.fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        } catch (e: Throwable) {
            AndroidInjection.inject(this)
        }

        if (savedInstanceState == null) {
            val fragment: Fragment = when {
                intent.data != null -> QuakeDetailFragment.newInstance(intent.data.lastPathSegment)
                else -> QuakeDetailFragment.newInstance()
            }

            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit()
        }
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
                QuakeDetailModule::class
        ))
        internal abstract fun quakeDetailFragment(): QuakeDetailFragment

        @ContributesAndroidInjector(modules = arrayOf(
                QuakeMap.Module::class
        ))
        internal abstract fun quakeMapFragment(): QuakeMapFragment

        @dagger.Module
        internal companion object {
            @JvmStatic
            @Provides
            fun featureObservable(
                    activity: FeatureDetailActivity
            ): Observable<Feature> = Observable
                    .just(activity.intent.getParcelableExtra<Feature>(EXTRA_FEATURE))

            @JvmStatic
            @Provides
            fun viewModel(activity: FeatureDetailActivity, factory: ViewModel.Factory) =
                    ViewModelProviders.of(activity, factory).get(ViewModel::class.java)
        }
    }
}
