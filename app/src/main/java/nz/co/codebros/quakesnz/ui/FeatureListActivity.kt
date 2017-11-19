package nz.co.codebros.quakesnz.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.settings.SettingsActivity
import javax.inject.Inject

class FeatureListActivity : AppCompatActivity(), HasSupportFragmentInjector,
        QuakeListFragment.OnFeatureClickedListener {

    @Inject
    internal lateinit var dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>
    private var mTwoPane: Boolean = false

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            dispatchingSupportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_feature_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        mTwoPane = findViewById<View>(R.id.map_container) != null
        if (mTwoPane && supportFragmentManager.findFragmentById(R.id.map_container) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.map_container, QuakeMapFragment())
                    .commit()
        }
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
