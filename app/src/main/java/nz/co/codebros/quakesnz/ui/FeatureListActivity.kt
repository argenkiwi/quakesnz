package nz.co.codebros.quakesnz.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.list.QuakeListFragment

class FeatureListActivity : AppCompatActivity(), QuakeListFragment.OnFeatureClickedListener {

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_list)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        mTwoPane = findViewById(R.id.feature_detail_container) != null
    }

    override fun onFeatureClicked(view: View) {
        if (!mTwoPane) {
            val intent = FeatureDetailActivity.newIntent(this)
            val activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, view, getString(R.string.transition_name))
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_info -> {
                startActivity(Intent(this, InfoActivity::class.java))
                return true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
