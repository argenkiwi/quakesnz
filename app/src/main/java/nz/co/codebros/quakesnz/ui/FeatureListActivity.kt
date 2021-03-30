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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.about.AboutActivity
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListModel
import nz.co.codebros.quakesnz.map.view.QuakeMapFragment
import nz.co.codebros.quakesnz.settings.SettingsActivity
import nz.co.codebros.quakesnz.util.toLiveData
import javax.inject.Inject

@AndroidEntryPoint
class FeatureListActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()

    private var mTwoPane = false

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

        viewModel.quakeListEvents.observe(this) {
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

    @HiltViewModel
    class ViewModel @Inject constructor(
            private val quakeListModel: QuakeListModel
    ) : androidx.lifecycle.ViewModel() {
        val quakeListEvents
            get() = quakeListModel.eventObservable.toLiveData(BackpressureStrategy.LATEST)
    }
}
