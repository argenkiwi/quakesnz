package nz.co.codebros.quakesnz.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.about.AboutActivity
import nz.co.codebros.quakesnz.list.QuakeListViewModel
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.map.view.QuakeMapFragment
import nz.co.codebros.quakesnz.settings.SettingsActivity

@AndroidEntryPoint
class FeatureListActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    private val viewModel: QuakeListViewModel by viewModels()

    private var mTwoPane = false

    @ExperimentalCoroutinesApi
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

        viewModel.liveEvents.observe(this) {
            if (it is QuakeListEvent.SelectQuake) {
                if (!mTwoPane) {
                    startActivity(FeatureDetailActivity.newIntent(this, it.feature))
                }
            }
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
