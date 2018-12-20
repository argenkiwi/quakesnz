package nz.co.codebros.quakesnz.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.about.AboutActivity
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListModel
import nz.co.codebros.quakesnz.list.view.QuakeListFragment
import nz.co.codebros.quakesnz.map.model.QuakeMapState
import nz.co.codebros.quakesnz.map.view.QuakeMapFragment
import nz.co.codebros.quakesnz.scope.FragmentScope
import nz.co.codebros.quakesnz.settings.SettingsActivity
import nz.co.codebros.quakesnz.util.toLiveData
import nz.co.vilemob.daggerviewmodel.DaggerViewModel
import nz.co.vilemob.daggerviewmodel.appcompat.DaggerViewModelActivity
import javax.inject.Inject

class FeatureListActivity : DaggerViewModelActivity<FeatureListActivity.ViewModel>() {

    private var mTwoPane = false

    override fun onCreateViewModel(viewModelProvider: ViewModelProvider) =
            viewModelProvider.get(ViewModel::class.java)

    override fun onViewModelCreated(viewModel: ViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.quakeListEvents.observe(this, Observer {
            when (it) {
                is QuakeListEvent.SelectQuake -> when {
                    !mTwoPane -> startActivity(FeatureDetailActivity.newIntent(this, it.feature))
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_list)

        findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
            title = title
        }

        mTwoPane = findViewById<View>(R.id.map_container) != null
        if (mTwoPane && supportFragmentManager.findFragmentById(R.id.map_container) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.map_container, QuakeMapFragment())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        R.id.action_about -> {
            startActivity(Intent(this, AboutActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    class ViewModel @Inject constructor(
            private val quakeListModel: QuakeListModel
    ) : DaggerViewModel() {
        val quakeListEvents
            get() = quakeListModel.eventObservable.toLiveData(BackpressureStrategy.LATEST)
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        internal abstract fun quakeListFragment(): QuakeListFragment

        @FragmentScope
        @ContributesAndroidInjector
        internal abstract fun quakeMapFragment(): QuakeMapFragment

        @dagger.Module
        companion object {

            @JvmStatic
            @Provides
            fun quakeMapState(
                    quakeListModel: QuakeListModel
            ) = Transformations.map(quakeListModel.state) {
                QuakeMapState(it.selectedFeature?.geometry?.coordinates)
            }
        }
    }
}
