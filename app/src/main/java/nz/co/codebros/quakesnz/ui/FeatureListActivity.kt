package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.settings.SettingsActivity
import javax.inject.Inject

class FeatureListActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var viewModel: FeatureListActivityViewModel

    private var mTwoPane = false
    private var featureId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            viewModel = ViewModelProviders.of(this)
                    .get(FeatureListActivityViewModel::class.java)
        } catch (t: Throwable) {
            AndroidInjection.inject(this)
        }

        featureId = savedInstanceState?.getString("featureId")

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

        viewModel.liveFeature.observe(this, Observer {
            it?.let {
                if (it.properties.publicId != featureId) {
                    featureId = it.properties.publicId
                    if (!mTwoPane) startActivity(FeatureDetailActivity.newIntent(this, it))
                }
            }
        })
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("featureId", featureId)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            viewModel.dispatchingSupportFragmentInjector
}
