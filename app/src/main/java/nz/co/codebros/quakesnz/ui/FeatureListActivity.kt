package nz.co.codebros.quakesnz.ui

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
import io.reactivex.disposables.Disposable
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.settings.SettingsActivity
import javax.inject.Inject

class FeatureListActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var viewModel: FeatureListActivityViewModel

    private var mTwoPane = false
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            viewModel = ViewModelProviders.of(this)
                    .get(FeatureListActivityViewModel::class.java)
        } catch (t: Throwable) {
            AndroidInjection.inject(this)
        }

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

        val connectableObservable = viewModel.featureObservable.publish()
        connectableObservable.connect()
        disposable = connectableObservable.subscribe({
            if (!mTwoPane) startActivity(FeatureDetailActivity.newIntent(this, it))
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            viewModel.dispatchingSupportFragmentInjector
}
