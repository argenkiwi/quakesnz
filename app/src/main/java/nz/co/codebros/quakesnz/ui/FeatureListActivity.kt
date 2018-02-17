package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ar.soflete.daggerlifecycle.appcompat.DaggerViewModelActivity
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.list.QuakeListEvent
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.settings.SettingsActivity

class FeatureListActivity : DaggerViewModelActivity<FeatureListActivityViewModel>() {

    override val viewModelClass: Class<FeatureListActivityViewModel>
        get() = FeatureListActivityViewModel::class.java

    private var mTwoPane = false

    override fun onBindViewModel(viewModel: FeatureListActivityViewModel) {
        super.onBindViewModel(viewModel)
        viewModel.quakeListEvents.liveData.observe(this, Observer {
            when (it) {
                is QuakeListEvent.SelectQuake -> when {
                    !mTwoPane -> startActivity(FeatureDetailActivity.newIntent(this, it.quake))
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
}
