package nz.co.codebros.quakesnz.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.usecase.Result
import nz.co.codebros.quakesnz.detail.model.QuakeDetailEvent
import nz.co.codebros.quakesnz.detail.model.QuakeDetailModel
import nz.co.codebros.quakesnz.detail.view.QuakeDetailFragment
import javax.inject.Inject

@AndroidEntryPoint
class FeatureDetailActivity : FragmentActivity() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        when (savedInstanceState) {
            null -> {
                supportFragmentManager.beginTransaction()
                        .add(android.R.id.content, QuakeDetailFragment())
                        .commit()
            }
        }

        intent.handle()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
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

    @HiltViewModel
    class ViewModel @Inject constructor(
            private val quakeDetailModel: QuakeDetailModel
    ) : androidx.lifecycle.ViewModel() {

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
