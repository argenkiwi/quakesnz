package nz.co.codebros.quakesnz.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.usecase.Result
import nz.co.codebros.quakesnz.detail.model.QuakeDetailEvent
import nz.co.codebros.quakesnz.detail.model.QuakeDetailModel
import nz.co.codebros.quakesnz.detail.view.QuakeDetailFragment
import nz.co.codebros.quakesnz.map.model.QuakeMapState
import nz.co.codebros.quakesnz.map.view.QuakeMapFragment
import nz.co.vilemob.daggerviewmodel.DaggerViewModel
import nz.co.vilemob.daggerviewmodel.appcompat.DaggerViewModelActivity
import javax.inject.Inject

class FeatureDetailActivity : DaggerViewModelActivity<FeatureDetailActivity.ViewModel>() {

    private lateinit var viewModel: ViewModel

    override fun onCreateViewModel(viewModelProvider: ViewModelProvider) =
            viewModelProvider.get(ViewModel::class.java)

    override fun onViewModelCreated(viewModel: ViewModel) {
        super.onViewModelCreated(viewModel)
        this.viewModel = viewModel
        intent.handle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        when (savedInstanceState) {
            null -> {
                supportFragmentManager.beginTransaction()
                        .add(android.R.id.content, QuakeDetailFragment())
                        .commit()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.handle()
        setIntent(intent)
    }

    private fun Intent.handle() {
        feature?.let { viewModel.onFeatureLoaded(it) }
        data?.lastPathSegment?.let { viewModel.onLoadFeature(it) }
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

    class ViewModel @Inject constructor(
            private val quakeDetailModel: QuakeDetailModel
    ) : DaggerViewModel() {

        private val disposable = quakeDetailModel.subscribe()

        override fun onCleared() {
            super.onCleared()
            disposable.dispose()
        }

        fun onFeatureLoaded(feature: Feature) {
            quakeDetailModel.publish(QuakeDetailEvent.LoadQuakeComplete(Result.Success(feature)))
        }

        fun onLoadFeature(publicId: String) {
            quakeDetailModel.publish(QuakeDetailEvent.LoadQuake(publicId))
        }
    }

    @dagger.Module
    internal abstract class Module {
        @ContributesAndroidInjector
        internal abstract fun quakeDetailFragment(): QuakeDetailFragment

        @ContributesAndroidInjector
        internal abstract fun quakeMapFragment(): QuakeMapFragment

        @dagger.Module
        internal companion object {

            @JvmStatic
            @Provides
            fun quakeMapState(
                    quakeDetailModel: QuakeDetailModel
            ): LiveData<QuakeMapState> = Transformations.map(quakeDetailModel.state) {
                QuakeMapState(it.feature?.geometry?.coordinates)
            }
        }
    }

    companion object {
        private const val EXTRA_FEATURE = "extra_feature"

        private var Intent.feature: Feature?
            set(value) {
                putExtra(EXTRA_FEATURE, value)
            }
            get() = getParcelableExtra(EXTRA_FEATURE)

        fun newIntent(
                context: Context,
                feature: Feature
        ): Intent = Intent(context, FeatureDetailActivity::class.java)
                .apply { this.feature = feature }
    }
}
